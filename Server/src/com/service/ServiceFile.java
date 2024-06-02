package com.service;

import com.app.MessageType;
import com.connection.DatabaseConnection;
import com.model.ModelFile;
import com.model.ModelFileReceiver;
import com.model.ModelFileSender;
import com.model.ModelPackageSender;
import com.model.ModelReceiveImage;
import com.model.ModelSendMessage;
import com.swing.blurHash.BlurHash;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ServiceFile {

    public ServiceFile() {
        this.con = DatabaseConnection.getInstance().getConnection();
        this.fileReceivers = new HashMap<>();
        this.fileSenders = new HashMap<>();
    }

    public ModelFile addFileReceiver(String fileExtension) throws SQLException {
        ModelFile data;
        PreparedStatement p = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
        p.setString(1, fileExtension);
        p.execute();
        ResultSet r = p.getGeneratedKeys();
        r.first();
        int fileID = r.getInt(1);
        data = new ModelFile(fileID, fileExtension);
        r.close();
        p.close();
        return data;
    }

    public void updateBlurHashDone(int fileID, String blurhash) throws SQLException {
        PreparedStatement p = con.prepareStatement(UPDATE_BLUR_HASH_DONE);
        p.setString(1, blurhash);
        p.setInt(2, fileID);
        p.execute();
        p.close();
    }

    public void updateDone(int fileID) throws SQLException {
        PreparedStatement p = con.prepareStatement(UPDATE_DONE);
        p.setInt(1, fileID);
        p.execute();
        p.close();
    }

    public void initFile(ModelFile file, ModelSendMessage message) throws IOException {
        fileReceivers.put(file.getFileID(), new ModelFileReceiver(message, toFileObject(file)));
    }

    public ModelFile getFile(int fileID) throws SQLException {
        PreparedStatement p = con.prepareStatement(GET_FILE_EXTENSION);
        p.setInt(1, fileID);
        ResultSet r = p.executeQuery();
        r.first();
        String fileExtension = r.getString(1);
        ModelFile data = new ModelFile(fileID, fileExtension);
        r.close();
        p.close();
        return data;
    }

    public synchronized ModelFile initFile(int fileID) throws IOException, SQLException {
        ModelFile file;
        if (!fileSenders.containsKey(fileID)) {
            file = getFile(fileID);
            fileSenders.put(fileID, new ModelFileSender(file, new File(PATH_FILE + fileID + file.getFileExtension())));
        } else {
            file = fileSenders.get(fileID).getData();
        }
        return file;
    }

    public byte[] getFileData(long currentLength, int fileID) throws IOException, SQLException {
        initFile(fileID);
        return fileSenders.get(fileID).read(currentLength);
    }

    public long getFileSize(int fileID) {
        return fileSenders.get(fileID).getFileSize();
    }

    public void receiveFile(ModelPackageSender dataPackage) throws IOException {
        if (!dataPackage.isFinish()) {
            fileReceivers.get(dataPackage.getFileID()).writeFile(dataPackage.getData());
        } else {
            fileReceivers.get(dataPackage.getFileID()).close();
        }
    }

    public ModelSendMessage closeFile(ModelReceiveImage dataImage) throws IOException, SQLException {
        ModelFileReceiver file = fileReceivers.get(dataImage.getFileID());
        if (file.getMessage().getMessageType() == MessageType.IMAGE.getValue()) {
            //  Image file
            //  So create blurhash image string
            file.getMessage().setText("");
            String blurhash = convertFileToBlurHash(file.getFile(), dataImage);
            updateBlurHashDone(dataImage.getFileID(), blurhash);
        } else {
            updateDone(dataImage.getFileID());
        }
        fileReceivers.remove(dataImage.getFileID());
        //  Get message to send to target client when file receive finish
        return file.getMessage();
    }

    private String convertFileToBlurHash(File file, ModelReceiveImage dataImage) throws IOException {
        BufferedImage img = ImageIO.read(file);
        Dimension size = getAutoSize(new Dimension(img.getWidth(), img.getHeight()), new Dimension(200, 200));
        //  Convert image to small size
        BufferedImage newImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        g2.drawImage(img, 0, 0, size.width, size.height, null);
        String blurhash = BlurHash.encode(newImage);
        dataImage.setWidth(size.width);
        dataImage.setHeight(size.height);
        dataImage.setImage(blurhash);
        return blurhash;
    }

    private Dimension getAutoSize(Dimension fromSize, Dimension toSize) {
        int w = toSize.width;
        int h = toSize.height;
        int iw = fromSize.width;
        int ih = fromSize.height;
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.min(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        return new Dimension(width, height);
    }

    private File toFileObject(ModelFile file) {
        return new File(PATH_FILE + file.getFileID() + file.getFileExtension());
    }

    //  SQL
    private final String PATH_FILE = "server_data/";
    private final String INSERT = "INSERT INTO files (FileExtension) VALUES (?)";
    private final String UPDATE_BLUR_HASH_DONE = "UPDATE files SET BlurHash=?, `Status`='1' WHERE FileID=? LIMIT 1";
    private final String UPDATE_DONE = "UPDATE FILES SET `Status`='1' WHERE FileID=? LIMIT 1";
    private final String GET_FILE_EXTENSION = "SELECT FileExtension FROM files WHERE FileID=? LIMIT 1";
    //  Instance
    private final Connection con;
    private final Map<Integer, ModelFileReceiver> fileReceivers;
    private final Map<Integer, ModelFileSender> fileSenders;
}
