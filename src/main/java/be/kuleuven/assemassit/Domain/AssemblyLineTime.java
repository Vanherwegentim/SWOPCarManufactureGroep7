package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Repositories.OvertimeRepository;

import java.time.LocalTime;

/**
 * @mutable
 * @invar | getOverTimeRepository() != null
 */
public class AssemblyLineTime {

  /**
   * @representationObject
   * @invar | overTimeRepository != null
   */
  private LocalTime openingTime;
  /**
   * @representationObject
   */
  private LocalTime closingTime;
  /**
   * @representationObject
   */
  private OvertimeRepository overTimeRepository;
  private int overtime;

  /**
   * @post | getOpeningTime() != null
   * @post | getClosingtime() != null
   * @post | getOvertimeRepository() != null
   * @post | getOverTime() == getOvertimeRepository().getOverTime()
   */
  public AssemblyLineTime() {
    this.openingTime = LocalTime.of(6, 0);
    this.closingTime = LocalTime.of(22, 0);
    this.overTimeRepository = new OvertimeRepository();
    this.overtime = overTimeRepository.getOverTime();
  }

  /**
   * @throws IllegalArgumentException | overTimeRepository == null
   * @post | getOpeningTime() != null
   * @post | getClosingtime() != null
   * @post | getOvertimeRepository().equals(overTimeRepository)
   * @post | getOverTime() == getOvertimeRepository().getOverTime()
   */
  public AssemblyLineTime(LocalTime openingTime, LocalTime closingTime, OvertimeRepository overTimeRepository) {
    if (overTimeRepository == null)
      throw new IllegalArgumentException();
    this.openingTime = openingTime;
    this.closingTime = closingTime;
    this.overTimeRepository = overTimeRepository;
  }

  /**
   * Update the overtime that will be used for the scheduling of the next day
   *
   * @param overtime the new overtime
   * @mutates | this
   * @post | getOvertime() == overtime
   */
  public void update(int overtime) {
    if (overtime < 0) {
      throw new IllegalArgumentException("Overtime can not be lower than 0");
    }
    this.overtime = overtime;
    this.overTimeRepository.setOverTime(overtime);
  }

  public LocalTime getOpeningTime() {
    return openingTime;
  }

  /**
   * Sets the start time of the assembly line.
   *
   * @param startTime
   * @throws IllegalArgumentException startTime can not be null | startTime == null
   * @post | getOpeningTime() == startTime
   */
  public void setOpeningTime(LocalTime startTime) {
    if (startTime == null) {
      throw new IllegalArgumentException("StartTime can not be null");
    }
    this.openingTime = startTime;
  }

  public LocalTime getClosingTime() {
    return closingTime;
  }

  /**
   * Sets the end time of the assembly line.
   * This is always set by the car manufacturing company and is not outside of the opening hours of the company.
   *
   * @param endTime
   * @throws IllegalArgumentException endTime can not be null | endTime == null
   * @post | this.endTime == endTime
   */
  public void setClosingTime(LocalTime endTime) {
    if (endTime == null) {
      throw new IllegalArgumentException("EndTime can not be null");
    }
    this.closingTime = endTime;
  }

  public int getOvertime() {
    return overtime;
  }


  public OvertimeRepository getOverTimeRepository() {
    return overTimeRepository;
  }
}
