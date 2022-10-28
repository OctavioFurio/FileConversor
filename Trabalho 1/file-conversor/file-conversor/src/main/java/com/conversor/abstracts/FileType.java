package com.conversor.abstracts;

public enum FileType {
    CSV(1),
    HTML(2),
    XML(3);

    int value = 0;

    FileType(int value) {
        this.value = value;
    }

    public static FileType fromInt(int value) {
        switch (value) {
            case 1:
                return CSV;
            case 2:
                return HTML;
            case 3:
                return XML;
            default:
                return null;
        }
    }

    public static FileType getFileType(String fileExtension) {
        switch (fileExtension) {
            case "csv":
                return FileType.CSV;
            case "html":
                return FileType.HTML;
            case "xml":
                return FileType.XML;
            default:
                return null;
        }
    }
}
