package com.conversor.conversors;

import com.conversor.abstracts.*;
import com.conversor.conversors.models.*;

public class FileConversor extends DataFile {

    public FileConversor(String fileName, String content, FileType fileType) {
        super(fileName, content, fileType);
    }

    public static DataFile convertFile(FileConversor fileConversor, FileType fileType) {
        return fileConversor.convertToFormat(fileType);
    }

    /*
     * Converte o texto de uma string para lowerCamelCase
     * @param s: String
     * @return String
     */
    public String toCamelCase(String s) {
        String[] parts = s.split(" ");
        String camelCaseString = "";
        for (String part : parts) {
            camelCaseString = camelCaseString + toProperCase(part);
        }
        return camelCaseString.substring(0, 1).toLowerCase() + camelCaseString.substring(1);
    }

    public String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /*
     * Converte o texto de uma string em lowerCamelCase para Title Case
     * @param camelCaseString: String
     * @return String
     */
    public String toTitleCase(String camelCaseString) {
        camelCaseString = camelCaseString.substring(0, 1).toUpperCase() + camelCaseString.substring(1);
        String[] parts = camelCaseString.split("(?=\\p{Upper})");
        String titleCaseString = "";
        for (String part : parts) {
            titleCaseString = titleCaseString + part + " ";
        }
        return titleCaseString.substring(0, titleCaseString.length() - 1);
    }

    /*
     * Retorna uma instância do tipo arquivo lido (CSVFile, HTMLFile ou XMLFile), dado seus parâmetros
     * @param fileName: String
     * @param fileContent: String
     * @param fileExtension: String
     * @return DataFile
     */
    public static DataFile createFile(String fileName, String fileContent, String fileExtension) {
        switch(FileType.getFileType(fileExtension))
        {
            case CSV:
                return new CSVFile(fileName, fileContent);
            case HTML:
                return new HTMLFile(fileName, fileContent);
            case XML:
                return new XMLFile(fileName, fileContent);  
            default:
                return null;
        }
    }

    @Override
    public DataFile convertToFormat(FileType fileType) {
        return null; // Método abstrato
    }

    
    /*
     * Divide o texto em linhas com base em uma string chave
     * @param text: String
     * @param split: String
     * @return String[]
     */
    public String[] getRows(String text, String split){
        return text.split(split);
    }
}
