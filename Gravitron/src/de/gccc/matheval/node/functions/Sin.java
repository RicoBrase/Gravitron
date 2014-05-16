package de.gccc.matheval.node.functions;

import de.gccc.matheval.node.DefaultFunctionNode;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.node.leaves.Parameter;

public class Sin extends DefaultFunctionNode {

    public Sin(Node parameter) {
        super(parameter);
    }

    public Sin() {
    }

    @Override
    public String getName() {
        return "sin";
    }

    @Override
    public double calculate() {
        return Math.sin(getParameter().calculate());
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public Node cloneTree() {
        return new Sin(getParameter().cloneTree());
    }

    @Override
    public Node cloneTreeForDerivative() {
        return new Sin(getParameter().cloneTreeForDerivative());
    }

    @Override
    protected Node differentiateOuter(Parameter variable) {
        return new Cos(getParameter().cloneTreeForDerivative());
    }
}
