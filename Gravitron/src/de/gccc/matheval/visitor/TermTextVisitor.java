package de.gccc.matheval.visitor;

import de.gccc.matheval.node.FunctionNode;
import de.gccc.matheval.node.OperatorNode;
import de.gccc.matheval.node.leaves.Constant;
import de.gccc.matheval.node.leaves.Literal;
import de.gccc.matheval.node.leaves.Parameter;

public class TermTextVisitor extends TextVisitor {


    @Override
    public void visitEnter(FunctionNode node) {
        buffer += node.getName() + "(";
    }

    @Override
    public void visitEnter(OperatorNode node) {
        if (bracketsRequired(node)) {
            buffer += "(";
        }
    }

    @Override
    public void visitBetween(FunctionNode node) {
        buffer += ", ";
    }

    @Override
    public void visitBetween(OperatorNode node) {
        buffer += " " + node.getSymbol() + " ";
    }

    @Override
    public void visitLeave(FunctionNode node) {
        buffer += ")";
    }

    @Override
    public void visitLeave(OperatorNode node) {
        if (bracketsRequired(node)) {
            buffer += ")";
        }
    }

    @Override
    public void visit(Literal node) {
        buffer += String.valueOf(node.getValue());
    }

    @Override
    public void visit(Parameter node) {
        buffer += node.getName();
    }
    
    @Override
    public void visit(Constant node) {
        buffer += node.getName();
    }
    
    protected boolean bracketsRequired(OperatorNode node) {
        return node.getParent() != null 
                && node.getPriority() < node.getParent().getPriority();
    }
}
