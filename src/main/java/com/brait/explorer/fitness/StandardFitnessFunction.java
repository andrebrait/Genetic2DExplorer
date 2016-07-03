package com.brait.explorer.fitness;

import net.objecthunter.exp4j.Expression;

/**
 * Created by andre on 03/07/16.
 */
public class StandardFitnessFunction {

    private final Boolean min;
    private final Expression function;

    public StandardFitnessFunction(Expression function, Boolean min){
        this.min = min;
        this.function = function;

    }

    public Double evaluate(Double x, Double y){
        Double result = function.setVariable("x", x).setVariable("y", y).evaluate();
        return min ? -result : result;
    }

}
