package be.kuleuven.assemassit.Domain.Helper;

public interface Subject {
  void attach(Observer observer);

  void detach(Observer observer);

  void notifyObservers();
}
