package be.kuleuven.assemassit.Repositories;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class OvertimeRepository {
  private final String FILE_PATH = "/garage-holders.txt";
  private int overTime = -1;

  public int getOverTime() {
    if (this.overTime == -1)
      readOverTime();
    return this.overTime;
  }

  /**
   * Set the over time with the new over time, this will be used by the scheduling algorithm on the next work day
   *
   * @param overTime the new over time
   * @throws IllegalStateException | overTime < 0
   */
  public void setOverTime(int overTime) {
    if (overTime < 0)
      throw new IllegalArgumentException("Over time can not be lower than zero");

    this.overTime = overTime;
  }

  private void readOverTime() {
    try (Scanner input = new Scanner(getClass().getResourceAsStream(FILE_PATH))) {
      if (input.hasNext()) {
        this.overTime = input.nextInt();
      } else {
        this.overTime = 0;
      }
    } catch (NullPointerException e) {
      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
      this.overTime = 0;
    }
  }

  private void writeOverTime() {
    try {
      FileWriter writer = new FileWriter(FILE_PATH, false);
      writer.write(Integer.toString(this.overTime));
      writer.close();
    } catch (IOException e) {
      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
    }
  }

  /**
   * Clear the file where the overtime is stored
   */
  public void clearFile() {
    try {
      new FileWriter(FILE_PATH, false).close();
    } catch (IOException e) {
      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
    }
  }
}
