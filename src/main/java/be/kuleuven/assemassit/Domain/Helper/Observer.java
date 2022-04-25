package be.kuleuven.assemassit.Domain.Helper;

public interface Observer {
  void update(Object observerable, Object value);
}
