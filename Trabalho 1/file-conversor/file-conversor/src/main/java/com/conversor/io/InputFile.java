package com.conversor.io;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.Scanner;

import com.conversor.abstracts.DataFile;
import com.conversor.conversors.FileConversor;

public class InputFile {
    private String fileName;
    private Scanner streamScanner;
    private File stream;
    private final String path = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
    private final String pathPrefix = "/inputFiles/";

    public InputFile(String fileName) {
        this.fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        try {
            stream = new File(path + pathPrefix + fileName);
            streamScanner = new Scanner(stream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getStreamContent() {
        String content = "";
        while (streamScanner.hasNextLine()) {
            String line = streamScanner.nextLine();
            content = content + line + System.lineSeparator();
        }
        return content;
    }

    public DataFile streamDataToDataFile() {
        String fileContent = getStreamContent();
        String fileExtension = stream.getName().split("\\.")[1];
        return FileConversor.createFile(this.fileName, fileContent, fileExtension);
    }
}
