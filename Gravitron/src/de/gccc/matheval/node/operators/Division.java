package de.gccc.matheval.node.operators;

import de.gccc.matheval.node.DerivativeUnsupportedException;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.node.OperatorNode;
import de.gccc.matheval.node.leaves.Parameter;

public class Division extends OperatorNode {

    public Division(Node paramA, Node paramB) {
        super(paramA, paramB);
    }

    public Division() {
    }

    @Override
    public double calculate() {
        return paramA.calculate() / paramB.calculate();
    }

    @Override
    public Node differentiate(Parameter variable) throws DerivativeUnsupportedException {
        Node numerator = new Addition(
                new Multiplication(paramA.differentiate(variable), paramB.cloneTreeForDerivative()),
                new Multiplication(paramA.cloneTreeForDerivative(), paramB.differentiate(variable)));
        Node denominator = new Multiplication(paramB.cloneTreeForDerivative(), paramB.cloneTreeForDerivative());

        return new Division(numerator, denominator);
    }

    @Override
    public Node cloneTree() {
        return new Division(paramA.cloneTree(), paramB.cloneTree());
    }

    @Override
    public Node cloneTreeForDerivative() {
        return new Division(paramA.cloneTreeForDerivative(), paramB.cloneTreeForDerivative());
    }

    @Override
    public int getPriority() {
        return PRIORITY_NORMAL;
    }

    @Override
    public String getSymbol() {
        return "/";
    }
}
