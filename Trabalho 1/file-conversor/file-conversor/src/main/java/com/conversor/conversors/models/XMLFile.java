package com.conversor.conversors.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.conversor.abstracts.DataFile;
import com.conversor.abstracts.FileType;
import com.conversor.abstracts.IFileConversor;
import com.conversor.conversors.FileConversor;

public class XMLFile extends FileConversor implements IFileConversor {

  public XMLFile(String fileName, String content) {
    super(fileName, content, FileType.XML);
  }

  @Override
  public CSVFile convertToCSV() {
    String[] vetorLinhas = getContent().split("\r\n");
    List<String> columnNames = getColumnNames(Arrays.asList(getContent().split("\r\n")));

    String newContent = "";
    for (int i = 3; i < columnNames.size(); i++)
      newContent += columnNames.get(i) + ",";

    for (int i = 3; i < vetorLinhas.length - 2; i++) {
      if ((i - 3) % (columnNames.size() - 1) == 0)
        newContent += "\n";
      newContent += getValues(Arrays.asList(vetorLinhas[i]));
    }
    newContent = newContent.replace("[]", "").replace("[", "").replace("]", ",");

    return new CSVFile(getFileName(), newContent);
  }

  @Override
  public HTMLFile convertToHTML() {
    return convertToCSV().convertToHTML();
  }

  @Override
  public XMLFile convertToXML() {
    return this;
  }

  /*
   * Retorna uma nova instância contendo os dados do XML no formato solicitado
   * 
   * @param fileType: FileType
   * 
   * @return DataFile
   */
  @Override
  public DataFile convertToFormat(FileType fileType) {
    switch (fileType) {
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
   * Método auxiliar para identificar as linhas de um documento XML,
   * separadas pela tag <row>
   * 
   * @param lines: String[]
   * 
   * @return List<String>: Linhas do documento
   */
  private List<String> getRows(String[] lines) {
    List<String> rows = new ArrayList<String>();
    for (int i = 1; i < lines.length; i++) {
      lines[i] = lines[i].strip();
      if (!lines[i].isBlank() && lines[i].contains("</row>"))
        rows.add(lines[i].replace("</row>", "").strip());
    }
    return rows;
  }

  /*
   * Método auxiliar para identificar as colunas de um documento XML
   * Converte o texto de dentro das tags XML para Title Case, e adiciona
   * na lista de colunas
   * 
   * @param rows: List<String>
   * 
   * @return List<String>: Lista de colunas
   */
  private List<String> getColumnNames(List<String> rows) {
    List<String> columnNames = new ArrayList<String>();
    for (String row : rows) {
      String[] columns = row.replace("/", "").split("<");
      for (String column : columns) {
        if (column.contains(">")) {
          column = toTitleCase(column.substring(0, column.indexOf(">")));
          if (!columnNames.contains(column)) {
            columnNames.add(column);
          }
        }
      }
    }
    return columnNames;
  }

  /*
   * Método auxiliar para identificar os valores das colunas de um documento XML,
   * que ficam entre as tags <nomeColuna></nomeColuna>
   * 
   * @param rows: List<String>
   * 
   * @return List<String>: Lista de valores
   */
  private List<String> getValues(List<String> rows) {
    List<String> values = new ArrayList<String>();
    for (String row : rows) {
      String[] columns = row.split(">");
      for (String column : columns) {
        column = column.substring(0, column.indexOf("<")).strip();
        if (!column.isBlank())
          values.add(column);
      }
    }
    return values;
  }
}
