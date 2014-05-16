package de.gccc.matheval.node.functions;

import de.gccc.matheval.node.DerivativeUnsupportedException;
import de.gccc.matheval.node.FunctionNode;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.node.leaves.Parameter;
import de.gccc.matheval.node.operators.Division;

public class Tan extends FunctionNode {

    public Tan(Node parameter) {
        super(parameter);
    }

    public Tan() {
    }

    @Override
    public String getName() {
        return "tan";
    }

    @Override
    public double calculate() {
        return Math.tan(getParameter().calculate());
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public Node cloneTree() {
        return new Tan(getParameter().cloneTree());
    }

    @Override
    public Node cloneTreeForDerivative() {
        return new Tan(getParameter().cloneTreeForDerivative());
    }

    @Override
    public int getMinParameterCount() {
        return 1;
    }

    @Override
    public int getMaxParameterCount() {
        return 1;
    }

    @Override
    public Node differentiate(Parameter variable) throws DerivativeUnsupportedException {
        Division div = new Division(
                new Sin(getParameter().cloneTreeForDerivative()),
                new Cos(getParameter().cloneTreeForDerivative()));

        return div.differentiate(variable);
    }
}
