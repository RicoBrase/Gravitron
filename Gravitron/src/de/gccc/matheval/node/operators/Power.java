package de.gccc.matheval.node.operators;

import de.gccc.matheval.node.DerivativeUnsupportedException;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.node.OperatorNode;
import de.gccc.matheval.node.functions.Exp;
import de.gccc.matheval.node.functions.Ln;
import de.gccc.matheval.node.leaves.Parameter;

public class Power extends OperatorNode {

    public Power(Node paramA, Node paramB) {
        super(paramA, paramB);
    }

    public Power() {
    }

    @Override
    public double calculate() {
        return Math.pow(paramA.calculate(), paramB.calculate());
    }

    @Override
    public Node differentiate(Parameter variable) throws DerivativeUnsupportedException {
        return new Exp(new Multiplication(new Ln(paramA.cloneTreeForDerivative()), paramB.cloneTreeForDerivative()))
                .differentiate(variable);
    }

    @Override
    public Node cloneTree() {
        return new Power(paramA.cloneTree(), paramB.cloneTree());
    }
    
    @Override
    public Node cloneTreeForDerivative() {
        return new Power(paramA.cloneTreeForDerivative(), paramB.cloneTreeForDerivative());
    }

    @Override
    public int getPriority() {
        return PRIORITY_HIGHER;
    }

    @Override
    public String getSymbol() {
        return "^";
    }
}
