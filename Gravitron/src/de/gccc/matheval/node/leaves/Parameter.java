package de.gccc.matheval.node.leaves;

import de.gccc.matheval.node.LeafNode;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.visitor.Visitor;

public class Parameter extends LeafNode {

    protected String name;
    protected double value;

    public Parameter(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (variable.getName().equals(getName())) {
            return new Literal(1);
        }

        return new Literal(0);
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public Node cloneTree() {
        return new Parameter(name, value);
    }

    @Override
    public Node cloneTreeForDerivative() {
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
