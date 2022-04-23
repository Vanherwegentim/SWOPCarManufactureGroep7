package be.kuleuven.assemassit.Domain.Helper;

import java.util.ArrayList;
import java.util.List;

public class MyEnhancedIterator<T> implements EnhancedIterator<T> {

  private List<T> elements;
  private int runner;

  public MyEnhancedIterator(List<T> data) {
    this.runner = 0;
    this.elements = new ArrayList<>(data);
  }

  @Override
  public T peek() {
    if (runner > elements.size())
      throw new IllegalStateException();
    return elements.get(runner);
  }

  @Override
  public boolean hasPrevious() {
    return runner == 0;
  }

  @Override
  public boolean hasNext() {
    return runner < elements.size();
  }

  @Override
  public T next() {
    return elements.get(runner++);
  }
}
