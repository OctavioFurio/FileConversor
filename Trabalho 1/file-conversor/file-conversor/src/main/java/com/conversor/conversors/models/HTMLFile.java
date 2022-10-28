package com.conversor.conversors.models;

import java.util.ArrayList;
import java.util.List;

import com.conversor.abstracts.DataFile;
import com.conversor.abstracts.FileType;
import com.conversor.abstracts.IFileConversor;
import com.conversor.conversors.FileConversor;

public class HTMLFile extends FileConversor implements IFileConversor {

    public HTMLFile(String fileName, String content) {
        super(fileName, content, FileType.HTML);
    }

    @Override
    public CSVFile convertToCSV() {
        String[] rows = getRows(getContent(), "</tr>");
        String newContent = getValue(rows[0], "<th>", "</th>");

        for(int i = 1; i < rows.length; i++)
            newContent += getValue(rows[i], "<td>", "</td>");

        return new CSVFile(getFileName(), newContent);
    }

    @Override
    public HTMLFile convertToHTML() {
        return this;
    }

    @Override
    public XMLFile convertToXML() {
        return convertToCSV().convertToXML();
    }

    /*
     * Retorna uma nova instância contendo os dados do HTML no formato solicitado
     * @param fileType: FileType
     * @return DataFile
     */
    @Override
    public DataFile convertToFormat(FileType fileType) {
        switch(fileType)
        {
            case CSV:
                return convertToCSV();
            case HTML:
                return convertToHTML();
            case XML:
                return convertToXML();
            default:
                return null;
        }
    }

    /*
     * Método auxiliar para ler os conteúdos uma linha de um arquivo HTML
     * Busca o valor usando duas palavras-chave
     * @param row: String
     * @param start: String
     * @param end: String
     * @return String
     */
    String getValue(String row, String start, String end)
    {
        String valueString = "";
        int offIndex = 0;
        int startIndex = row.indexOf(start, offIndex) + start.length();
        int endIndex = row.indexOf(end, offIndex);

        while(startIndex < endIndex)
        {
            String value = row.substring(startIndex, endIndex);
            valueString += value + ",";

            offIndex = endIndex + 1;
            startIndex = row.indexOf(start, offIndex) + start.length();
            endIndex = row.indexOf(end, offIndex);
        }

        valueString += "\n";

        return valueString;
    }
}
