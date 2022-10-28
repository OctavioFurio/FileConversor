package com.conversor.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;

public class OutputFile {
    private File stream;
    FileWriter streamWriter;
    private final String path = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
    private final String pathPrefix = "/outputFiles/";

    public OutputFile(String fileName, String fileExtension) {
        stream = new File(path + pathPrefix + fileName + "." + fileExtension);
    }

    public void contentToFile(String content) {
        try {
            streamWriter = new FileWriter(stream.getPath());
            streamWriter.write(content);
            streamWriter.close();

            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("It wasn't possible to write to file...");
            e.printStackTrace();
        }
    }
}
