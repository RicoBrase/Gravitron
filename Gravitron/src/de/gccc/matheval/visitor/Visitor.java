package de.gccc.matheval.visitor;

import de.gccc.matheval.node.CompositeNode;
import de.gccc.matheval.node.FunctionNode;
import de.gccc.matheval.node.LeafNode;
import de.gccc.matheval.node.OperatorNode;
import de.gccc.matheval.node.leaves.Constant;
import de.gccc.matheval.node.leaves.Literal;
import de.gccc.matheval.node.leaves.Parameter;

public interface Visitor {
    
    public void visitEnter(CompositeNode node);
    
    public void visitEnter(FunctionNode node);
    
    public void visitEnter(OperatorNode node);
    
    public void visitBetween(CompositeNode node);
    
    public void visitBetween(FunctionNode node);
    
    public void visitBetween(OperatorNode node);
    
    public void visitLeave(CompositeNode node);
    
    public void visitLeave(FunctionNode node);
    
    public void visitLeave(OperatorNode node);
    
    public void visit(LeafNode node);
    
    public void visit(Literal node);
    
    public void visit(Parameter node);
    
    public void visit(Constant node);
}
