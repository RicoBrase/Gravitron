package de.gccc.matheval.node;

import de.gccc.matheval.visitor.Visitor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FunctionNode extends CompositeNode {

    protected List<Node> parameters = new ArrayList<>();

    public FunctionNode(Node... parameters) {
        this.parameters = Arrays.asList(parameters);
    }

    public FunctionNode(List<Node> parameters) {
        this.parameters = parameters;
    }

    public FunctionNode() {
    }

    public void setParameter(Node param) {
        parameters = new ArrayList<>();
        parameters.add(param);
        param.setParent(this);
    }

    public void addParameter(Node param) {
        parameters.add(param);
        param.setParent(this);
    }

    public void removeParameter(int i) {
        parameters.get(i).setParent(null);
        parameters.remove(i);
    }

    public Node getParameter() {
        return getParameter(0);
    }

    public Node getParameter(int i) {
        return parameters.get(i);
    }

    public int getParameterCount() {
        return parameters.size();
    }

    @Override
    public int getPriority() {
        return PRIORITY_LOWEST;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitEnter(this);

        boolean firstParam = true;
        for (Node param : parameters) {
            if (firstParam) {
                firstParam = false;
            } else {
                visitor.visitBetween(this);
            }
            
            param.accept(visitor);
        }
        
        visitor.visitLeave(this);
    }

    public abstract String getName();

    public abstract int getMinParameterCount();

    public abstract int getMaxParameterCount();
}
