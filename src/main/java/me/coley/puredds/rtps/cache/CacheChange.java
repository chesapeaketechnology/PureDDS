package me.coley.puredds.rtps.cache;

import me.coley.puredds.rtps.GUID;
import me.coley.puredds.rtps.Handle;
import me.coley.puredds.rtps.structure.Writer;

/**
 * Some change to a data object.
 *
 * @author Matt Coley
 */
public class CacheChange {
	private final ChangeKind kind;
	private final GUID writerGuid;
	private final Handle instanceHandle;
	private final long sequenceNumber;
	private final Data data;
	// TODO: inlineQos (ParameterList)

	/**
	 * Create a cache change.
	 *
	 * @param kind
	 * 		Kind of change.
	 * @param writerGuid
	 * 		The {@link GUID} that identifies the RTPS {@link Writer} that made the change.
	 * @param instanceHandle
	 * 		Identifies the instance of the data-object to which the change applies.
	 * @param sequenceNumber
	 * 		Sequence number assigned by the RTPS {@link Writer} to uniquely identify the change.
	 * @param data
	 * 		Value associated with the change. May be {@code} depending on the kind.
	 */
	public CacheChange(ChangeKind kind, GUID writerGuid, Handle instanceHandle, long sequenceNumber, Data data) {
		this.kind = kind;
		this.writerGuid = writerGuid;
		this.instanceHandle = instanceHandle;
		this.sequenceNumber = sequenceNumber;
		this.data = data;
	}

	/**
	 * @return Kind of change.
	 */
	public ChangeKind getKind() {
		return kind;
	}

	/**
	 * @return {@link GUID} that identifies the RTPS {@link Writer} that made the change.
	 */
	public GUID getWriterGuid() {
		return writerGuid;
	}

	/**
	 * @return Identifies the instance of the data-object to which the change applies.
	 */
	public Handle getInstanceHandle() {
		// In DDS, the value of the fields labeled as ‘key’ within the data uniquely identify each data- object.
		return instanceHandle;
	}

	/**
	 * @return Sequence number assigned by the RTPS {@link Writer} to uniquely identify the change.
	 */
	public long getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @return Value associated with the change.
	 * Depending on the {@link #getKind() kind}, there may be no associated data <i>({@code null})</i>
	 */
	public Data getData() {
		return data;
	}
}
