package de.gccc.matheval.node;

import de.gccc.matheval.visitor.Visitor;

public abstract class OperatorNode extends CompositeNode {

    protected Node paramA;
    protected Node paramB;

    public OperatorNode(Node paramA, Node paramB) {
        this.paramA = paramA;
        this.paramB = paramB;
        paramA.setParent(this);
        paramB.setParent(this);
    }

    public OperatorNode() {
    }

    public Node getParamA() {
        return paramA;
    }

    public void setParamA(Node paramA) {
        this.paramA = paramA;
        paramA.setParent(this);
    }

    public Node getParamB() {
        return paramB;
    }

    public void setParamB(Node paramB) {
        this.paramB = paramB;
        paramB.setParent(this);
    }

    @Override
    public boolean isConstant() {
        return paramA.isConstant() && paramB.isConstant();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitEnter(this);
        paramA.accept(visitor);
        visitor.visitBetween(this);
        paramB.accept(visitor);
        visitor.visitLeave(this);
    }

    public abstract String getSymbol();
}
