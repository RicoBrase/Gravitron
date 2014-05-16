package de.gccc.matheval.parser;

import de.gccc.matheval.node.CompositeNode;
import de.gccc.matheval.node.FunctionNode;
import de.gccc.matheval.node.Node;
import de.gccc.matheval.node.OperatorNode;
import de.gccc.matheval.node.functions.Cos;
import de.gccc.matheval.node.functions.Exp;
import de.gccc.matheval.node.functions.Ln;
import de.gccc.matheval.node.functions.Sin;
import de.gccc.matheval.node.functions.Tan;
import de.gccc.matheval.node.leaves.Constant;
import de.gccc.matheval.node.leaves.Literal;
import de.gccc.matheval.node.leaves.Parameter;
import de.gccc.matheval.node.operators.Addition;
import de.gccc.matheval.node.operators.Division;
import de.gccc.matheval.node.operators.Multiplication;
import de.gccc.matheval.node.operators.Power;
import de.gccc.matheval.node.operators.Subtraction;
import de.gccc.matheval.parser.tokenizer.GenericTokenizer;
import de.gccc.matheval.parser.tokenizer.MathTokenizer;
import de.gccc.matheval.parser.tokenizer.Snapshot;
import java.util.HashMap;
import java.util.Map;

/**
 * A parser that transforms a mathematical expression to a tree structure (see {@link Node}).
 *
 * By default, you can use the following elements in your expressions:
 * <ul>
 * <li><b>Operators:</b> + (Addition), - (Subtraction), * (Multiplication), / (Division), ^ (Power)
 * <li><b>Functions:</b> sin, cos, tan, exp, ln
 * <li><b>Constants:</b> pi, e
 * </ul>
 *
 * You can use
 * {@link #addOperator(de.gccc.matheval.node.OperatorNode)}, {@link #addFunction(de.gccc.matheval.node.FunctionNode)}
 * and {@link #addConstant(de.gccc.matheval.node.leaves.Constant)} to add more elements.
 *
 * @author Daniel Seemaier
 */
public class ExpressionParser {

    private static final double DEFAULT_PARAMETER_VALUE = 0;
    protected Map<String, Constant> constants = new HashMap<>();
    protected Map<String, OperatorNode> operators = new HashMap<>();
    protected Map<String, FunctionNode> functions = new HashMap<>();
    /**
     * Cache to prevent multiple parameter objects with the same name during one parsing process.
     */
    private Map<String, Parameter> parameters = new HashMap<>();

    public ExpressionParser() {
        registerDefaults();
    }

    public void addConstant(Constant c) {
        constants.put(c.getName(), c);
    }

    public void addOperator(OperatorNode op) {
        operators.put(op.getSymbol(), op);
    }

    public void addFunction(FunctionNode func) {
        functions.put(func.getName(), func);
    }

    /**
     * Transforms the mathematical expression <em>term</em> to a tree structure.
     * 
     * @param term Your mathematical expression.
     * @return The resulting tree structure, hidden by an {@link Expression} object.
     * @throws ParsingException If an unknown or unexpected token occurs or a parenthesis is missing.
     */
    public Expression parse(String term) throws ParsingException {
        parameters.clear(); // clear cache
        GenericTokenizer tok = new MathTokenizer(term);
        return new Expression(buildNode(tok, -1), parameters);
    }

    protected boolean isConstant(String name) {
        return constants.containsKey(name);
    }

    private Node buildNode(GenericTokenizer tok, int limit) throws ParsingException {
        Snapshot nextOp = findLowestOperator(tok, limit);
        if (nextOp != null) {
            OperatorNode op = getNewOperator(nextOp.getValue());
            op.setParamA(buildNode(tok, nextOp.getTokenPosition()));
            op.setParamB(buildNode(tok, limit));
            return op;
        }

        Node node = null;

        loop:
        while (tok.find()) {
            // limit exceeded?
            if (limit != -1 && tok.getTokenPosition() >= limit) {
                break;
            }

            String id = tok.getIdentifier();
            String value = tok.getValue();

            // unexpected tokens?
            if (node != null && !id.equals(MathTokenizer.T_BRACKET_CLOSED)) {
                throw new SyntaxException("Unexpected " + id + " at position " + tok.getTokenPosition() + ": " + value);
            }

            switch (id) {
                case MathTokenizer.T_NUMBER:
                    node = new Literal(Double.valueOf(value));
                    break;

                case MathTokenizer.T_BRACKET_OPEN:
                    node = buildNode(tok, limit);
                    break;

                case MathTokenizer.T_BRACKET_CLOSED:
                    break loop;

                case MathTokenizer.T_CONSTANT:
                    if (constants.containsKey(value)) {
                        node = constants.get(value);
                    } else {
                        if (!parameters.containsKey(value)) {
                            parameters.put(value, new Parameter(value, DEFAULT_PARAMETER_VALUE));
                        }

                        node = parameters.get(value);
                    }
                    break;

                case MathTokenizer.T_FUNCTION:
                    node = buildFunctionNode(tok);
                    break;
            }
        }

        return node;
    }

    private Node buildFunctionNode(GenericTokenizer tok) throws ParsingException {
        Snapshot start = tok.getSnapshot();
        FunctionNode node = getNewFunction(tok.getValue());

        // check for left parenthesis
        if (!tok.find() || !tok.getIdentifier().equals(MathTokenizer.T_BRACKET_OPEN)) {
            throw new SyntaxException("No left parenthesis after function token (instead " + tok.getValue() + ")");
        }

        do {
            Snapshot del = findParameterEnd(tok);

            if (del == null) {
                throw new SyntaxException("Function starting at position " + start.getTokenPosition()
                        + " was never closed.");
            }

            node.addParameter(buildNode(tok, del.getTokenPosition()));
        } while (tok.getIdentifier().equals(MathTokenizer.T_PARAMETER_DEL));

        // validate parameter count
        int cur = node.getParameterCount();
        int max = node.getMaxParameterCount();
        int min = node.getMinParameterCount();

        if (cur > max) {
            throw new SyntaxException("Function has too many parameters (" + cur + " > " + max + ").");
        } else if (cur < min) {
            throw new SyntaxException("Function has too few parameters (" + cur + " < " + min + ").");
        }

        return node;
    }

    private Snapshot findParameterEnd(GenericTokenizer tok) throws ParsingException {
        Snapshot start = tok.getSnapshot();
        Snapshot result = null;

        boolean exit = false;
        while (tok.find() && !exit) {
            switch (tok.getIdentifier()) {
                case MathTokenizer.T_BRACKET_OPEN:
                    skipBrackets(tok);
                    break;

                case MathTokenizer.T_BRACKET_CLOSED:
                case MathTokenizer.T_PARAMETER_DEL:
                    result = tok.getSnapshot();
                    exit = true;
                    break;
            }
        }

        tok.reset(start);
        return result;
    }

    private Snapshot findLowestOperator(GenericTokenizer tok, int limit) throws ParsingException {
        Snapshot start = tok.getSnapshot();
        Snapshot result = null;
        int lowestPriority = CompositeNode.PRIORITY_HIGHEST;

        boolean exit = false;
        while (tok.find() && !exit) {
            // limit exceeded?
            if (limit != -1 && tok.getTokenPosition() >= limit) {
                break;
            }

            switch (tok.getIdentifier()) {
                case MathTokenizer.T_BRACKET_OPEN:
                    skipBrackets(tok);
                    break;

                case MathTokenizer.T_BRACKET_CLOSED:
                    exit = true;
                    break;

                case MathTokenizer.T_OPERATOR:
                    OperatorNode op = findOperator(tok.getValue());

                    if (op.getPriority() <= lowestPriority) {
                        lowestPriority = op.getPriority();
                        result = tok.getSnapshot();
                    }
                    break;
            }
        }

        tok.reset(start);
        return result;
    }

    private void skipBrackets(GenericTokenizer tok) throws ParsingException {
        Snapshot start = tok.getSnapshot();

        while (tok.find()) {
            switch (tok.getIdentifier()) {
                case MathTokenizer.T_BRACKET_OPEN:
                    skipBrackets(tok);
                    break;

                case MathTokenizer.T_BRACKET_CLOSED:
                    return;
            }
        }

        throw new SyntaxException("Left parenthesis at position " + start.getPosition() + " was never closed.");
    }

    private OperatorNode findOperator(String name) throws ParsingException {
        if (operators.containsKey(name)) {
            return operators.get(name);
        }

        throw new ParsingException("Unknown operator: " + name);
    }

    private FunctionNode findFunction(String name) throws ParsingException {
        if (functions.containsKey(name)) {
            return functions.get(name);
        }

        throw new ParsingException("Unknown function: " + name);
    }

    private OperatorNode getNewOperator(String name) throws ParsingException {
        try {
            return (OperatorNode) findOperator(name).getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new ParsingException("Operator does not provide an empty constructor.", ex);
        }
    }

    private FunctionNode getNewFunction(String name) throws ParsingException {
        try {
            return (FunctionNode) findFunction(name).getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new ParsingException("Function does not provide an empty constructor.", ex);
        }
    }

    private void registerDefaults() {
        addOperator(new Addition());
        addOperator(new Subtraction());
        addOperator(new Multiplication());
        addOperator(new Division());
        addOperator(new Power());
        addFunction(new Exp());
        addFunction(new Ln());
        addFunction(new Sin());
        addFunction(new Cos());
        addFunction(new Tan());
        addConstant(new Constant("pi", Math.PI));
        addConstant(new Constant("e", Math.E));
    }
}
