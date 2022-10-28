package com.conversor.abstracts;

public abstract class DataFile {
    private String fileName;
    private String content;
    private FileType fileType;

    public DataFile(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
    }

    public DataFile(String fileName, String content, FileType fileType) {
        this.fileName = fileName;
        this.content = content;
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public abstract DataFile convertToFormat(FileType fileType);
}
