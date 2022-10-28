package com.conversor.conversors.models;

import javax.print.DocFlavor.STRING;

import com.conversor.abstracts.DataFile;
import com.conversor.abstracts.FileType;
import com.conversor.abstracts.IFileConversor;
import com.conversor.conversors.FileConversor;

public class CSVFile extends FileConversor implements IFileConversor {

  public CSVFile(String fileName, String content) {
    super(fileName, content, FileType.CSV);
  }

  @Override
  public CSVFile convertToCSV() {
    return this;
  }

  @Override
  public HTMLFile convertToHTML() {
    String[] rows = getRows(getContent(), "\n");
    String newContent = "<html>\n\n\t<body>\n\t\t<table>\n";
    {
      newContent += "\t\t\t<tr>\n";
      String values[] = getValues(rows[0]);
      for (String value : values) {
        if ((int) value.charAt(value.length() - 1) == 13)
          continue;
        newContent += "\t\t\t\t<th>" + value + "</th>\n";
      }
      newContent += "\t\t\t</tr>\n";
    }
    for (int r = 1; r < rows.length; r++) {
      newContent += "\t\t\t<tr>\n";

      String values[] = getValues(rows[r]);
      for (String value : values) {
        if ((int) value.charAt(value.length() - 1) == 13)
          continue;
        newContent += "\t\t\t\t<td>" + value + "</td>\n";
      }
      newContent += "\t\t\t</tr>\n";
    }
    newContent += "\t\t</table>\n\t</body>\n\n</html>";

    return new HTMLFile(getFileName(), newContent);
  }

  @Override
  public XMLFile convertToXML() {
    String[] rows = getRows(getContent(), "\n");
    String[] firstRowValues = getValues(rows[0]);

    for (int i = 0; i < firstRowValues.length; i++)
      firstRowValues[i] = toCamelCase(firstRowValues[i]);

    String newContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n";

    for (int r = 1; r < rows.length; r++) {
      newContent += "\t<row>\n";
      String values[] = getValues(rows[r]);
      for (int v = 0; v < values.length; v++) {
        if ((int) values[v].charAt(values[v].length() - 1) == 13)
          continue;

        newContent += "\t\t<" + firstRowValues[v] + ">" + values[v] + "</" + firstRowValues[v] + ">\n";
      }
      newContent += "\t</row>\n";
    }
    newContent += "</root>";

    return new XMLFile(getFileName(), newContent);
  }

  /*
   * Retorna uma nova instância contendo os dados do CSV no formato solicitado
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
   * Método auxiliar para ler os conteúdos uma linha de um arquivo CSV
   * Utiliza um regex para separar os campos da linha
   * Exemplo: "1,2,3,4,5" -> ["1", "2", "3", "4", "5"]
   * 
   * @param line: String
   * 
   * @return String[]
   */
  private String[] getValues(String line) {
    return line.split(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");
  }
}
