package com.controller.service;

import com.model.app.MessageType;
import com.controller.connection.DatabaseConnection;
import com.model.ModelFile;
import com.model.ModelFileReceiver;
import com.model.ModelFileSender;
import com.model.ModelPackageSender;
import com.model.ModelReceiveImage;
import com.model.ModelSendMessage;
import com.view.swing.blurHash.BlurHash;
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

public class ServiceFileController {

    public ServiceFileController() {
        this.con = DatabaseConnection.getInstance().getConnection();
        this.fileReceivers = new HashMap<>();
        this.fileSenders = new HashMap<>();
    }

    public ModelFile addFileReceiver(String fileExtension) throws SQLException {
        ModelFile data;
        try (PreparedStatement p = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            p.setString(1, fileExtension);
            p.execute();
            try (ResultSet r = p.getGeneratedKeys()) {
                r.first();
                int fileID = r.getInt(1);
                data = new ModelFile(fileID, fileExtension);
            }
        }
        return data;
    }

    public void updateBlurHashDone(int fileID, String blurhash) throws SQLException {
        try (PreparedStatement p = con.prepareStatement(UPDATE_BLUR_HASH_DONE)) {
            p.setString(1, blurhash);
            p.setInt(2, fileID);
            p.execute();
        }
    }

    public void updateDone(int fileID) throws SQLException {
        try (PreparedStatement p = con.prepareStatement(UPDATE_DONE)) {
            p.setInt(1, fileID);
            p.execute();
        }
    }

    public void initFile(ModelFile file, ModelSendMessage message) throws IOException {
        System.out.println("Initializing file receiver for file ID: " + file.getFileID());
        fileReceivers.put(file.getFileID(), new ModelFileReceiver(message, toFileObject(file)));
    }

    public ModelFile getFile(int fileID) throws SQLException {
        ModelFile data;
        try (PreparedStatement p = con.prepareStatement(GET_FILE_EXTENSION)) {
            p.setInt(1, fileID);
            try (ResultSet r = p.executeQuery()) {
                r.first();
                String fileExtension = r.getString(1);
                data = new ModelFile(fileID, fileExtension);
            }
        }
        return data;
    }

    public synchronized ModelFile initFile(int fileID) throws IOException, SQLException {
        ModelFile file;
        if (!fileSenders.containsKey(fileID)) {
            System.out.println("Initializing file sender for file ID: " + fileID);
            file = getFile(fileID);
            fileSenders.put(fileID, new ModelFileSender(file, new File(PATH_FILE + fileID + file.getFileExtension())));
        } else {
            file = fileSenders.get(fileID).getData();
        }
        return file;
    }

    public byte[] getFileData(long currentLength, int fileID) throws IOException, SQLException {
        System.out.println("Getting file data for file ID: " + fileID + ", current length: " + currentLength);
        initFile(fileID);
        return fileSenders.get(fileID).read(currentLength);
    }

    public long getFileSize(int fileID) {
        return fileSenders.get(fileID).getFileSize();
    }

    public void receiveFile(ModelPackageSender dataPackage) throws IOException {
        System.out.println("Receiving file data package for file ID: " + dataPackage.getFileID());
        if (!dataPackage.isFinish()) {
            fileReceivers.get(dataPackage.getFileID()).writeFile(dataPackage.getData());
        } else {
            System.out.println("File receiving finished for file ID: " + dataPackage.getFileID());
            fileReceivers.get(dataPackage.getFileID()).close();
        }
    }

    public ModelSendMessage closeFile(ModelReceiveImage dataImage) throws IOException, SQLException {
        ModelFileReceiver file = fileReceivers.get(dataImage.getFileID());
        if (file.getMessage().getMessageType() == MessageType.IMAGE.getValue()) {
            System.out.println("Creating blurhash for image file ID: " + dataImage.getFileID());
            file.getMessage().setText("");
            String blurhash = convertFileToBlurHash(file.getFile(), dataImage);
            updateBlurHashDone(dataImage.getFileID(), blurhash);
        } else {
            updateDone(dataImage.getFileID());
        }
        fileReceivers.remove(dataImage.getFileID());
        return file.getMessage();
    }

    private String convertFileToBlurHash(File file, ModelReceiveImage dataImage) throws IOException {
        BufferedImage img = ImageIO.read(file);
        Dimension size = getAutoSize(new Dimension(img.getWidth(), img.getHeight()), new Dimension(200, 200));
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
    private final String INSERT = "INSERT INTO images (FileExtension) VALUES (?)";
    private final String UPDATE_BLUR_HASH_DONE = "UPDATE images SET BlurHash=?, `Status`='1' WHERE FileID=? LIMIT 1";
    private final String UPDATE_DONE = "UPDATE images SET `Status`='1' WHERE FileID=? LIMIT 1";
    private final String GET_FILE_EXTENSION = "SELECT FileExtension FROM images WHERE FileID=? LIMIT 1";
    //  Instance
    private final Connection con;
    private final Map<Integer, ModelFileReceiver> fileReceivers;
    private final Map<Integer, ModelFileSender> fileSenders;
}
