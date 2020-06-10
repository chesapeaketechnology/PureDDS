package me.coley.puredds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.DataRepresentation;

import java.util.ArrayList;
import java.util.List;

/**
 * DataRepresentation impl.
 *
 * @author Matt Coley
 * @see DataRepresentation
 */
public class DataRepresentationImpl extends AbstractPolicy implements DataRepresentation {
	private final List<Short> value;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param value
	 * 		values of representation types.
	 */
	public DataRepresentationImpl(ServiceEnvironment environment, List<Short> value) {
		super(environment);
		this.value = value;
	}

	/**
	 * @return values of representation types.
	 *
	 * @see DataRepresentation.Id
	 */
	@Override
	public List<Short> getValue() {
		return value;
	}

	@Override
	public DataRepresentation withValue(List<Short> value) {
		return new DataRepresentationImpl(getEnvironment(), value);
	}

	@Override
	public DataRepresentation withValue(short... value) {
		List<Short> list = new ArrayList<>(value.length);
		for (short s : value) {
			list.add(s);
		}
		return new DataRepresentationImpl(getEnvironment(), list);
	}

	@Override
	public Comparable<DataRepresentation> requestedOfferedContract() {
		return new DataRepresentationImpl(getEnvironment(), value);
	}

	@Override
	public int compareTo(DataRepresentation o) {
		return getValue().equals(o.getValue()) ? 0 : -1;
	}
}
