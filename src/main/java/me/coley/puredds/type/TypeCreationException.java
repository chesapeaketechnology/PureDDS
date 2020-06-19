package me.coley.puredds.type;

/**
 * Exception for when the given type cannot be created.
 *
 * @author Matt Coley
 */
public class TypeCreationException extends RuntimeException {
	private final Class<?> type;

	/**
	 * @param ex
	 * 		Cause exception.
	 * @param type
	 * 		Type that could not be created.
	 */
	public TypeCreationException(Exception ex, Class<?> type) {
		super(ex);
		this.type = type;
	}

	/**
	 * @return Type that could not be instantiated.
	 */
	public Class<?> getType() {
		return type;
	}
}
