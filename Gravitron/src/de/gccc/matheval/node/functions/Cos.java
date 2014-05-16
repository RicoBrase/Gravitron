package de.gccc.matheval.node.functions;

import de.gccc.matheval.node.DefaultFunctionNode;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.node.leaves.Literal;
import de.gccc.matheval.node.leaves.Parameter;
import de.gccc.matheval.node.operators.Multiplication;

public class Cos extends DefaultFunctionNode {

    public Cos(Node parameter) {
        super(parameter);
    }

    public Cos() {
    }

    @Override
    public String getName() {
        return "cos";
    }

    @Override
    public double calculate() {
        return Math.cos(getParameter().calculate());
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public Node cloneTree() {
        return new Cos(getParameter().cloneTree());
    }

    @Override
    public Node cloneTreeForDerivative() {
        return new Cos(getParameter().cloneTreeForDerivative());
    }

    @Override
    protected Node differentiateOuter(Parameter variable) {
        return new Multiplication(
                new Literal(-1),
                new Sin(getParameter().cloneTreeForDerivative()));
    }
}
