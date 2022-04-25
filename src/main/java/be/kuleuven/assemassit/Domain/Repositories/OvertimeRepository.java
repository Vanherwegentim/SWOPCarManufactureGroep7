package be.kuleuven.assemassit.Domain.Repositories;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class OvertimeRepository {
  private final String FILE_PATH = "src/main/resources/over-time.txt";
  private int overTime = -1;

  public int getOverTime() {
    if (this.overTime == -1)
      readOverTime();
    return this.overTime;
  }

  public void setOverTime(int overTime) {
    if (overTime < 0)
      throw new IllegalArgumentException("Over time can not be lower than zero");

    this.overTime = overTime;
    writeOverTime();
  }

  private void readOverTime() {
    try (Scanner input = new Scanner(new FileReader(FILE_PATH))) {
      if (input.hasNext()) {
        this.overTime = input.nextInt();
      } else {
        this.overTime = 0;
      }
    } catch (FileNotFoundException e) {
      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
    }

    clearFile();
    this.overTime = 0;
  }

  private void writeOverTime() {
    try {
      FileWriter writer = new FileWriter(FILE_PATH, false);
      writer.write(this.overTime);
      writer.close();
    } catch (IOException e) {
      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
    }
  }

  private void clearFile() {
    try {
      new FileWriter(FILE_PATH, false).close();
    } catch (IOException e) {
      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
    }
  }
}
