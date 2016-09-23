package game_engine;

import java.util.function.BinaryOperator;

public enum Operator {
    
    PRODUCT((n1, n2) -> n1 * n2),
    DIFFERENCE((n1, n2) -> n1-n2),
    SUM((n1, n2) -> n1 + n2),
    QUOTIENT((n1,n2) -> n1 / n2),
    REMAINDER((n1,n2) -> n1 % n2),
    POWER((n1,n2) -> Math.pow(n1, n2)),
    EQUALS((n1, n2) -> n2);

    private final BinaryOperator<Double> operation;

    private Operator(BinaryOperator<Double> operation) {
        this.operation = operation;
    }

    public double operate(double n1, double n2) {
        return operation.apply(n1, n2);
    }
}