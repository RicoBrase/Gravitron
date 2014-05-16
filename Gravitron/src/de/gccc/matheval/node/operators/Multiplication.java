package de.gccc.matheval.node.operators;

import de.gccc.matheval.node.DerivativeUnsupportedException;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.node.OperatorNode;
import de.gccc.matheval.node.leaves.Parameter;

public class Multiplication extends OperatorNode {

    public Multiplication(Node paramA, Node paramB) {
        super(paramA, paramB);
    }

    public Multiplication() {
    }

    @Override
    public double calculate() {
        return paramA.calculate() * paramB.calculate();
    }

    @Override
    public Node differentiate(Parameter variable) throws DerivativeUnsupportedException {
        return new Addition(
                new Multiplication(paramA.differentiate(variable), paramB.cloneTreeForDerivative()),
                new Multiplication(paramA.cloneTreeForDerivative(), paramB.differentiate(variable)));
    }

    @Override
    public Node cloneTree() {
        return new Multiplication(paramA.cloneTree(), paramB.cloneTree());
    }
    
    @Override
    public Node cloneTreeForDerivative() {
        return new Multiplication(paramA.cloneTreeForDerivative(), paramB.cloneTreeForDerivative());
    }

    @Override
    public int getPriority() {
        return PRIORITY_NORMAL;
    }

    @Override
    public String getSymbol() {
        return "*";
    }
}
