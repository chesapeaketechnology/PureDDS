package me.coley.puredds.rtps.cache;

import me.coley.puredds.rtps.GUID;
import me.coley.puredds.rtps.structure.Reader;
import me.coley.puredds.rtps.structure.Writer;

/**
 * Container to store temporary sets of changes to data-objects made by {@link Writer writers}.
 * The entire history does not have to be stored, merely only what is necessary to satisfy corresponding {@link me.coley.puredds.sub.DataReaderImpl DataReader}.
 * The detail of the partial history depends on the configured quality of service and the state of communication with the matched {@link Writer}.
 *
 * @author Matt Coley
 */
public class ReaderCache extends HistoryCache {
	/**
	 * @param guid
	 * 		Parent RTPS {@link Reader#getGuid() Writer's GUID}.
	 */
	public ReaderCache(GUID guid) {
		super(guid);
	}
}
