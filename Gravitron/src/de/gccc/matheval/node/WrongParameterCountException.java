package de.gccc.matheval.node;

@SuppressWarnings("serial")
public class WrongParameterCountException extends CalculationException {

    public WrongParameterCountException() {
    }

    public WrongParameterCountException(String message) {
        super(message);
    }

    public WrongParameterCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongParameterCountException(Throwable cause) {
        super(cause);
    }

    public WrongParameterCountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
