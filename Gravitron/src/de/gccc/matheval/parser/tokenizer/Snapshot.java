package de.gccc.matheval.parser.tokenizer;

public class Snapshot {

    private int position;
    private String identifier;
    private String value;

    public Snapshot(int position, String identifier, String value) {
        this.position = position;
        this.identifier = identifier;
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public int getTokenPosition() {
        if (position > 0) {
            return position - value.length();
        }

        return position;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Snapshot, Id: " + identifier + ", Value: " + value + ", Position: " + getTokenPosition() + " (real " + position + ")";
    }
}
