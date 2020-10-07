package me.coley.puredds.rtps.cache;

import me.coley.puredds.rtps.GUID;
import me.coley.puredds.rtps.structure.Reader;
import me.coley.puredds.rtps.structure.Writer;

/**
 * Container to store temporary sets of changes to data-objects made by {@link Writer writers}.
 * The entire history does not have to be stored, merely only what is necessary to satisfy {@link Reader reader} services.
 * The detail of the partial history depends on the configured quality of service.
 *
 * @author Matt Coley
 */
public class WriterCache extends HistoryCache {
	/**
	 * @param guid
	 * 		Parent RTPS {@link Writer#getGuid() Writer's GUID}.
	 */
	public WriterCache(GUID guid) {
		super(guid);
	}
}
