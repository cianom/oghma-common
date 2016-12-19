package io.lyra.oghma.common;

/**
 * An exception that occurred during initialization of the application.
 *
 * @author Cian.
 */
public class InitializationException extends OghmaException {

    public InitializationException(final String message) {
        super(message);
    }

    public InitializationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
