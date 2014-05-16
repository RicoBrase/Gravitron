package de.gccc.matheval.node.leaves;

import de.gccc.matheval.node.LeafNode;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.visitor.Visitor;

public class Literal extends LeafNode {

    protected double value;

    public Literal(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double calculate() {
        return value;
    }

    @Override
    public Node differentiate(Parameter variable) {
        return new Literal(0);
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public Node cloneTree() {
        return new Literal(value);
    }

    @Override
    public Node cloneTreeForDerivative() {
        return new Literal(value);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
