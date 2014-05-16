package de.gccc.matheval.node;

@SuppressWarnings("serial")
public class DerivativeUnsupportedException extends CalculationException {

    public DerivativeUnsupportedException() {
    }

    public DerivativeUnsupportedException(String message) {
        super(message);
    }

    public DerivativeUnsupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DerivativeUnsupportedException(Throwable cause) {
        super(cause);
    }

    public DerivativeUnsupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
