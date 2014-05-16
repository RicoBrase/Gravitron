package de.gccc.matheval.visitor;

import de.gccc.matheval.node.CompositeNode;
import de.gccc.matheval.node.FunctionNode;
import de.gccc.matheval.node.LeafNode;
import de.gccc.matheval.node.OperatorNode;
import de.gccc.matheval.node.leaves.Constant;
import de.gccc.matheval.node.leaves.Literal;
import de.gccc.matheval.node.leaves.Parameter;

public abstract class VisitorAdapter implements Visitor {

    @Override
    public void visitEnter(CompositeNode node) {
    }

    @Override
    public void visitEnter(FunctionNode node) {
    }

    @Override
    public void visitEnter(OperatorNode node) {
    }

    @Override
    public void visitBetween(CompositeNode node) {
    }

    @Override
    public void visitBetween(FunctionNode node) {
    }

    @Override
    public void visitBetween(OperatorNode node) {
    }

    @Override
    public void visitLeave(CompositeNode node) {
    }

    @Override
    public void visitLeave(FunctionNode node) {
    }

    @Override
    public void visitLeave(OperatorNode node) {
    }

    @Override
    public void visit(LeafNode node) {
    }

    @Override
    public void visit(Literal node) {
    }

    @Override
    public void visit(Parameter node) {
    }

    @Override
    public void visit(Constant node) {
    }
}
