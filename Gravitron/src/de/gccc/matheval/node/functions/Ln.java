package de.gccc.matheval.node.functions;

import de.gccc.matheval.node.DefaultFunctionNode;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.node.leaves.Literal;
import de.gccc.matheval.node.leaves.Parameter;
import de.gccc.matheval.node.operators.Division;

public class Ln extends DefaultFunctionNode {

    public Ln(Node parameter) {
        super(parameter);
    }

    public Ln() {
    }

    @Override
    public String getName() {
        return "ln";
    }

    @Override
    public double calculate() {
        return Math.log(getParameter().calculate());
    }

    @Override
    public boolean isConstant() {
        if (getParameter().isConstant() && getParameter().calculate() == 1) {
            return true;
        }

        return false;
    }

    @Override
    public Node cloneTree() {
        return new Ln(getParameter());
    }

    @Override
    public Node cloneTreeForDerivative() {
        return new Ln(getParameter().cloneTreeForDerivative());
    }

    @Override
    protected Node differentiateOuter(Parameter variable) {
        return new Division(new Literal(1), getParameter().cloneTreeForDerivative());
    }
}
