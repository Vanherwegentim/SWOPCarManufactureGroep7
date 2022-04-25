package be.kuleuven.assemassit.Domain.Enums;

public enum WorkPostType {
  CAR_BODY_POST {
    @Override
    public String toString() {
      return "Car Body Post";
    }
  }, DRIVETRAIN_POST {
    @Override
    public String toString() {
      return "Drivetrain Post";
    }
  }, ACCESSORIES_POST {
    @Override
    public String toString() {
      return "Accessories Post";
    }
  }
}
