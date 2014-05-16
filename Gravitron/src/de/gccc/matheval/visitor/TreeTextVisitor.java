package de.gccc.matheval.visitor;

import de.gccc.matheval.node.CompositeNode;
import de.gccc.matheval.node.FunctionNode;
import de.gccc.matheval.node.LeafNode;
import de.gccc.matheval.node.OperatorNode;
import de.gccc.matheval.node.leaves.Literal;
import de.gccc.matheval.node.leaves.Parameter;

public class TreeTextVisitor extends TextVisitor {

    private String prefix = new String();

    @Override
    public String getResult() {
        String result = super.getResult();
        return result.substring(0, result.length()
                - System.lineSeparator().length());
    }

    @Override
    public void reset() {
        prefix = new String();
        super.reset();
    }

    @Override
    public void visitEnter(CompositeNode node) {
        buffer += prefix + node.getClass().getName() + System.lineSeparator();
        prefix += "\t";
    }

    @Override
    public void visitEnter(FunctionNode node) {
        visitEnter((CompositeNode) node);
    }

    @Override
    public void visitEnter(OperatorNode node) {
        visitEnter((CompositeNode) node);
    }

    @Override
    public void visitLeave(CompositeNode node) {
        prefix = prefix.substring(0, prefix.length() - 1);
    }

    @Override
    public void visitLeave(FunctionNode node) {
        visitLeave((CompositeNode) node);
    }

    @Override
    public void visitLeave(OperatorNode node) {
        visitLeave((CompositeNode) node);
    }

    @Override
    public void visit(LeafNode node) {
        buffer += prefix + node.getClass().getName() + " -> " + node.calculate() + System.lineSeparator();
    }

    @Override
    public void visit(Literal node) {
        visit((LeafNode) node);
    }

    @Override
    public void visit(Parameter node) {
        visit((LeafNode) node);
    }
}
