package de.gccc.matheval.node.leaves;

import de.gccc.matheval.node.Node;
import de.gccc.matheval.visitor.Visitor;

public class Constant extends Literal {

    protected String name;

    public Constant(String name, double value) {
        super(value);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public Node cloneTree() {
        return new Constant(name, value);
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
