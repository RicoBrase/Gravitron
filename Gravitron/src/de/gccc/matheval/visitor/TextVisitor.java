package de.gccc.matheval.visitor;

public abstract class TextVisitor extends VisitorAdapter {

    protected String buffer = new String();
    
    public String getResult() {
        return buffer;
    }
    
    public String flushResult() {
        String result = getResult();
        reset();
        return result;
    }
    
    public void reset() {
        buffer = new String();
    }
}
