package de.gccc.matheval.node;

public abstract class CompositeNode extends Node {

    public static final int PRIORITY_HIGHEST = Integer.MAX_VALUE;
    public static final int PRIORITY_HIGHER = 5;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_LOWER = -5;
    public static final int PRIORITY_LOWEST = Integer.MIN_VALUE;
    
    public abstract int getPriority();
}
