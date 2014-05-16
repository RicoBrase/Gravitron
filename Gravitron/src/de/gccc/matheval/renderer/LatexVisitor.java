package de.gccc.matheval.renderer;

import de.gccc.matheval.node.FunctionNode;
import de.gccc.matheval.node.OperatorNode;
import de.gccc.matheval.node.leaves.Constant;
import de.gccc.matheval.visitor.TermTextVisitor;

public class LatexVisitor extends TermTextVisitor {

    @Override
    public void visitEnter(FunctionNode node) {
        buffer += "{";
        super.visitEnter(node);
        buffer += "{";
    }

    @Override
    public void visitEnter(OperatorNode node) {
        buffer += "{";
        if (node.getSymbol().equals("/")) {
            buffer += "\\frac";
        } else {
            super.visitEnter(node);
        }
        buffer += "{";
    }

    @Override
    public void visitBetween(FunctionNode node) {
        buffer += "}";
        super.visitBetween(node);
        buffer += "{";
    }

    @Override
    public void visitBetween(OperatorNode node) {
        buffer += "}";
        if (!node.getSymbol().equals("/")) {
            super.visitBetween(node);
        }
        buffer += "{";
    }

    @Override
    public void visitLeave(FunctionNode node) {
        buffer += "}";
        super.visitLeave(node);
        buffer += "}";
    }

    @Override
    public void visitLeave(OperatorNode node) {
        buffer += "}";
        if (!node.getSymbol().equals("/")) {
            super.visitLeave(node);
        }
        buffer += "}";
    }

    @Override
    public void visit(Constant node) {
        switch (node.getName()) {
            case "pi":
                buffer += "\\pi";
                break;

            default:
                super.visit(node);
        }
    }

    @Override
    protected boolean bracketsRequired(OperatorNode node) {
        if (node.getParent() instanceof OperatorNode && ((OperatorNode) node.getParent()).getSymbol().equals("/")) {
            return false;
        }
        
        return super.bracketsRequired(node);
    }
}
