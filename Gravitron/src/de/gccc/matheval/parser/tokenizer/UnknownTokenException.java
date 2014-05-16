package de.gccc.matheval.parser.tokenizer;

import de.gccc.matheval.parser.ParsingException;

@SuppressWarnings("serial")
public class UnknownTokenException extends ParsingException {

    public UnknownTokenException() {
    }

    public UnknownTokenException(String message) {
        super(message);
    }

    public UnknownTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownTokenException(Throwable cause) {
        super(cause);
    }

    public UnknownTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
