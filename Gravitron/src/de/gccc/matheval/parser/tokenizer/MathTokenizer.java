package de.gccc.matheval.parser.tokenizer;

public class MathTokenizer extends GenericTokenizer {

    public static final String T_OPERATOR = "Operator";
    public static final String T_NUMBER = "Number";
    public static final String T_CONSTANT = "Constant";
    public static final String T_FUNCTION = "Function";
    public static final String T_BRACKET_OPEN = "BracketOpen";
    public static final String T_BRACKET_CLOSED = "BracketClosed";
    public static final String T_PARAMETER_DEL = "ParameterDelimiter";

    public MathTokenizer(String subject) {
        super(subject.replaceAll(" ", ""));
        
        addToken(T_OPERATOR, "[\\Q+-*/^!§$%&\\E]{1}");
        addToken(T_NUMBER, "[\\d\\.]+");
        addToken(T_CONSTANT, "[a-z]+(?!\\()");
        addToken(T_FUNCTION, "[a-z]{2,}(?=\\()");
        addToken(T_BRACKET_OPEN, "\\(");
        addToken(T_BRACKET_CLOSED, "\\)");
        addToken(T_PARAMETER_DEL, ",");
    }
}
