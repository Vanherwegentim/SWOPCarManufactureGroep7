package be.kuleuven.assemassit.Domain.Helper;

import java.util.Iterator;

public interface EnhancedIterator<T> extends Iterator<T> {
  T peek();

  boolean hasPrevious();
}
