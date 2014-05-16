package de.gccc.matheval.node.functions;

import de.gccc.matheval.node.DefaultFunctionNode;
import de.gccc.matheval.node.FunctionNode;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.node.leaves.Parameter;

public class Exp extends DefaultFunctionNode {

    public Exp(Node parameter) {
        super(parameter);
    }

    public Exp() {
    }

    @Override
    public String getName() {
        return "exp";
    }

    @Override
    public double calculate() {
        return Math.pow(Math.E, getParameter().calculate());
    }

    @Override
    public boolean isConstant() {
        if (getParameter().isConstant() && getParameter().calculate() == 0) {
            return true;
        }

        return false;
    }

    @Override
    public Node cloneTree() {
        return new Exp(getParameter().cloneTree());
    }

    @Override
    public Node cloneTreeForDerivative() {
        return new Exp(getParameter().cloneTreeForDerivative());
    }

    @Override
    protected FunctionNode differentiateOuter(Parameter variable) {
        return new Exp(getParameter().cloneTreeForDerivative());
    }
}
