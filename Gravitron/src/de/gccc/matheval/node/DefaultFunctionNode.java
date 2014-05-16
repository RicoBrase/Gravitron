package de.gccc.matheval.node;

import de.gccc.matheval.node.leaves.Parameter;
import de.gccc.matheval.node.operators.Multiplication;

public abstract class DefaultFunctionNode extends FunctionNode {

    public DefaultFunctionNode(Node parameter) {
        super(parameter);
    }

    public DefaultFunctionNode() {
    }

    @Override
    public Node differentiate(Parameter variable) throws DerivativeUnsupportedException {
        Node outer = differentiateOuter(variable);
        Node inner = getParameter().differentiate(variable);
        
        return new Multiplication(outer, inner);
    }

    @Override
    public int getMinParameterCount() {
        return 1;
    }

    @Override
    public int getMaxParameterCount() {
        return 1;
    }

    protected abstract Node differentiateOuter(Parameter variable);
}
