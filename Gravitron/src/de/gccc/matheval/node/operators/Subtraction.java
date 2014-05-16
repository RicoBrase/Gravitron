package de.gccc.matheval.node.operators;

import de.gccc.matheval.node.DerivativeUnsupportedException;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.node.OperatorNode;
import de.gccc.matheval.node.leaves.Parameter;

public class Subtraction extends OperatorNode {

    public Subtraction(Node paramA, Node paramB) {
        super(paramA, paramB);
    }

    public Subtraction() {
    }

    @Override
    public double calculate() {
        return paramA.calculate() - paramB.calculate();
    }

    @Override
    public Node differentiate(Parameter variable) throws DerivativeUnsupportedException {
        return new Subtraction(paramA.differentiate(variable),
                paramB.differentiate(variable));
    }

    @Override
    public Node cloneTree() {
        return new Subtraction(paramA.cloneTree(), paramB.cloneTree());
    }

    @Override
    public Node cloneTreeForDerivative() {
        return new Subtraction(paramA.cloneTreeForDerivative(), paramB.cloneTreeForDerivative());
    }

    @Override
    public int getPriority() {
        return PRIORITY_LOWER;
    }

    @Override
    public String getSymbol() {
        return "-";
    }
}
