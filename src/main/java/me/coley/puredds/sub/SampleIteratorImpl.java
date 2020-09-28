package me.coley.puredds.sub;

import org.omg.dds.sub.Sample;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

/**
 * Sample iterator impl.
 *
 * @param <T>
 * 		Topic data type.
 *
 * @author Matt Coley
 */
public class SampleIteratorImpl<T> implements Sample.Iterator<T> {
	private final ListIterator<Sample<T>> delegate;

	/**
	 * Create a sample iterator.
	 *
	 * @param list
	 * 		List of samples to iterate over.
	 */
	public SampleIteratorImpl(List<Sample<T>> list) {
		this.delegate = list.listIterator();
	}

	@Override
	public void close() throws IOException {
		// No-op
	}

	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		return delegate.hasPrevious();
	}

	@Override
	public Sample<T> next() {
		return delegate.next();
	}

	@Override
	public Sample<T> previous() {
		return delegate.previous();
	}

	@Override
	public int nextIndex() {
		return delegate.nextIndex();
	}

	@Override
	public int previousIndex() {
		return delegate.previousIndex();
	}

	@Override
	public void remove() {
		delegate.remove();
	}

	@Override
	public void set(Sample<T> sample) {
		delegate.set(sample);
	}

	@Override
	public void add(Sample<T> sample) {
		delegate.add(sample);
	}
}
