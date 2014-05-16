package de.gccc.matheval.parser.tokenizer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The GenericTokenizer breaks a string into tokens using regular expressions.
 * <p>
 * Example of usage:
 *
 * <pre>
 * {@code
 * GenericTokenizer tokenizer = new GenericTokenizer("my subject string");
 * tokenizer.addToken("T_WORD", " |$");
 *
 * while (tokenizer.find()) {
 *  tokenizer.getIdentifier(); // T_WORD
 *  tokenizer.getValue(); // my, subject, string
 * }
 * }
 * </pre>
 *
 * You can use the {@link #reset(java.lang.String)} method to change the subject.
 *
 * @author Daniel
 */
public class GenericTokenizer {

    /**
     * All known tokens, stored as identifier and compiled regular expression.
     */
    private Map<String, Pattern> tokens = new HashMap<>();
    /**
     * String that is being broken into tokens.
     */
    private String subject;
    /**
     * The ending position of the current token.
     */
    private int position;
    /**
     * Identifier of the current token.
     */
    private String identifier;
    /**
     * Value of the current token.
     */
    private String value;

    public GenericTokenizer(String subject) {
        this.subject = subject;
    }

    /**
     * Registers a new type of token.
     *
     * @param identifier An unique name for this type of token.
     * @param pattern A regular expression to identify the token.
     */
    public void addToken(String identifier, String pattern) {
        tokens.put(identifier, Pattern.compile(pattern));
    }

    /**
     * Returns a snapshot of the component's current position.
     *
     * @see #reset(de.gccc.matheval.tokenizer.Snapshot)
     * @return A snapshot of the component's current position.
     */
    public Snapshot getSnapshot() {
        return new Snapshot(position, identifier, value);
    }

    /**
     * Finds the next token in the subject. If the end of the subject is reached, false will be returned (otherwise
     * true).
     *
     * @return FALSE, if the end of the subject is reached.
     * @throws UnknownTokenException If the end of the subject is <b>not</b>
     * reached, but no token matches.
     */
    public boolean find() throws UnknownTokenException {
        String residualSubject = subject.substring(position);

        for (String id : tokens.keySet()) {
            Matcher m = tokens.get(id).matcher(residualSubject);

            if (m.find() && m.start() == 0) {
                identifier = id;
                value = residualSubject.substring(0, m.end());
                position += m.end();
                return true;
            }
        }

        if (!residualSubject.isEmpty()) {
            throw new UnknownTokenException("Unknown token: "
                    + residualSubject);
        }

        return false;
    }

    /**
     * Sets all attributes to their default values.
     */
    public void reset() {
        position = 0;
        identifier = null;
        value = null;
    }

    /**
     * Sets the component's progress to a position specified by a snapshot.
     *
     * @param snapshot
     */
    public void reset(Snapshot snapshot) {
        position = snapshot.getPosition();
        identifier = snapshot.getIdentifier();
        value = snapshot.getValue();
    }

    /**
     * Sets the component's position to zero and changes its subject; registered tokens won't be affected.
     *
     * @param subject A new subject.
     */
    public void reset(String subject) {
        reset();
        this.subject = subject;
    }

    /**
     * Returns the starting position of the current token's value.
     *
     * @return The starting position of the current token's value.
     */
    public int getTokenPosition() {
        if (position > 0) {
            return position - value.length();
        }

        return position;
    }

    /**
     * Returns current token's identifier or NULL, if no token is yet found.
     *
     * @return
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns current token's value or NULL, if no token is yet found.
     *
     * @return
     */
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return "Tokenizer, Id: " + getIdentifier() + ", Value: " + getValue() + ", Position: " + getTokenPosition();
    }
}
