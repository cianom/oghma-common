package io.lyra.oghma.common;

/**
 * Base unchecked Oghma exception.
 *
 * @author Cian.
 */
public class OghmaException extends RuntimeException {

    public OghmaException(final String message) {
        super(message);
    }

    public OghmaException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OghmaException(final Throwable cause) {
        super(cause);
    }

}
