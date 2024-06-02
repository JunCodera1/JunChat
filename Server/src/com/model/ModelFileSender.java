package com.model;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ModelFileSender {

    public ModelFile getData() {
        return data;
    }

    public void setData(ModelFile data) {
        this.data = data;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public RandomAccessFile getAccFile() {
        return accFile;
    }

    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public ModelFileSender(ModelFile data, File file) throws IOException {
        this.data = data;
        this.file = file;
        this.accFile = new RandomAccessFile(file, "r");
        this.fileSize = accFile.length();
    }

    public ModelFileSender() {
    }

    private ModelFile data;
    private File file;
    private RandomAccessFile accFile;
    private long fileSize;

    public byte[] read(long currentLength) throws IOException {
        accFile.seek(currentLength);
        if (currentLength != fileSize) {
            int max = 2000;
            long length = currentLength + max >= fileSize ? fileSize - currentLength : max;
            byte[] b = new byte[(int) length];
            accFile.read(b);
            return b;
        } else {
            return null;
        }
    }
}
