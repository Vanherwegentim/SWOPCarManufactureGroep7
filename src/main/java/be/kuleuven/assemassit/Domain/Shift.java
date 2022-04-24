package be.kuleuven.assemassit.Domain;

import java.time.LocalTime;

public class Shift {
  private LocalTime startTime;
  private LocalTime endTime;
  private int previousOvertimeInMinutes;

  public Shift(LocalTime startTime, LocalTime endTime, int previousOvertimeInMinutes) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.previousOvertimeInMinutes = previousOvertimeInMinutes;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public int getPreviousOvertimeInMinutes() {
    return previousOvertimeInMinutes;
  }

  public void setPreviousOvertimeInMinutes(int previousOvertimeInMinutes) {
    this.previousOvertimeInMinutes = previousOvertimeInMinutes;
  }
}
