package de.gccc.matheval.node;

import de.gccc.matheval.node.leaves.Parameter;
import de.gccc.matheval.visitor.Visitor;

public abstract class Node {

    protected CompositeNode parent;

    public CompositeNode getParent() {
        return parent;
    }

    public void setParent(CompositeNode parent) {
        this.parent = parent;
    }
    
    public abstract double calculate();
    
    public abstract Node differentiate(Parameter variable) throws DerivativeUnsupportedException;
    
    public abstract boolean isConstant();
    
    public abstract Node cloneTree();
    
    public abstract Node cloneTreeForDerivative();
    
    public abstract void accept(Visitor visitor);
}
