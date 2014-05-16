package de.gccc.matheval.parser;

import de.gccc.matheval.node.DerivativeUnsupportedException;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.node.leaves.Parameter;
import de.gccc.matheval.renderer.NodeRenderer;
import de.gccc.matheval.visitor.TermTextVisitor;
import de.gccc.matheval.visitor.TreeTextVisitor;
import de.gccc.matheval.visitor.VisitorAdapter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Expression {

    private Map<String, Parameter> parameters = new HashMap<>();
    private Node tree;

    public Expression(Node tree, Map<String, Parameter> parameters) {
        this.tree = tree;
        this.parameters = parameters;
    }

    public Set<String> getParameterNames() {
        return Collections.unmodifiableSet(parameters.keySet());
    }

    public Parameter getParameter(String name) {
        if (parameters.containsKey(name)) {
            return parameters.get(name);
        }

        return null;
    }

    public void setParameter(String name, double value) {
        Parameter param = getParameter(name);

        if (param != null) {
            param.setValue(value);
        }
    }

    public double calculate() {
        return tree.calculate();
    }

    public Expression differentiate(String variable) throws DerivativeUnsupportedException {
        return differentiate(getParameter(variable));
    }

    public Expression differentiate(Parameter variable) throws DerivativeUnsupportedException {
        Node derivative = tree.differentiate(variable);
        final Map<String, Parameter> params = new HashMap<>();

        derivative.accept(new VisitorAdapter() {
            @Override
            public void visit(Parameter node) {
                if (params.containsKey(node.getName())) {
                    if (params.get(node.getName()) != node) {
                        System.err.println("Bug: " + node + " <-> " + params.get(node.getName()));
                    }
                } else {
                    params.put(node.getName(), node);
                }
            }
        });

        return new Expression(derivative, params);
    }

    public String toTermString() {
        TermTextVisitor visitor = new TermTextVisitor();
        tree.accept(visitor);
        return visitor.flushResult();
    }

    public String toTreeString() {
        TreeTextVisitor visitor = new TreeTextVisitor();
        tree.accept(visitor);
        return visitor.flushResult();
    }

    public BufferedImage render() {
        return render(new NodeRenderer());
    }

    public BufferedImage render(float size) {
        NodeRenderer renderer = new NodeRenderer();
        renderer.setSize(size);
        return render(renderer);
    }

    public BufferedImage render(Color bgColor, Color fgColor, float size) {
        NodeRenderer renderer = new NodeRenderer();
        renderer.setBackgroundColor(bgColor);
        renderer.setForegroundColor(fgColor);
        renderer.setSize(size);
        return render(renderer);
    }

    public BufferedImage render(NodeRenderer renderer) {
        return renderer.render(tree);
    }

    @Override
    public String toString() {
        return toTermString();
    }
}
