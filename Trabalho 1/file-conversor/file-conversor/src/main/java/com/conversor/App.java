/**
@author Octávio Xavier Fúrio 13781862
*/

package com.conversor;

import java.util.Scanner;

import com.conversor.abstracts.DataFile;
import com.conversor.abstracts.FileType;
import com.conversor.io.InputFile;
import com.conversor.io.OutputFile;

public class App {
  private static Scanner applicationInput = new Scanner(System.in);

  private static void showMenu() {
    System.out.println("[1] Insert file name");
    System.out.println("[0] Exit application");
  }

  private static int chooseOption() {
    showMenu();
    int option;
    option = applicationInput.nextInt();
    applicationInput.nextLine();
    return option;
  }

  private static void runSolution(String applicationStream, FileType fileType) {
    InputFile inputStream = new InputFile(applicationStream);
    DataFile dataFile = inputStream.streamDataToDataFile();
    DataFile convertedDataFile = dataFile.convertToFormat(fileType);
    OutputFile outputStream = new OutputFile(dataFile.getFileName(), fileType.toString().toLowerCase());
    outputStream.contentToFile(convertedDataFile.getContent());
  }

  public static void main(String[] args) {
    while (chooseOption() != 0) {

      System.out.println("Insira o nome do arquivo: ");
      String fileName = applicationInput.nextLine();
      System.out.println("Digite a opção do tipo de arquivo que deseja converter: ");
      System.out.println("[1] CSV");
      System.out.println("[2] HTML");
      System.out.println("[3] XML");
      int typeInput = applicationInput.nextInt();
      FileType fileType = FileType.fromInt(typeInput);
      if (fileType == null) {
        System.out.println("Opção inválida");
        continue;
      }
      runSolution(fileName, fileType);
    }
  }
}
