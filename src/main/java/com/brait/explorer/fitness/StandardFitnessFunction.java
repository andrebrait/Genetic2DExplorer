package com.brait.explorer.fitness;

import com.brait.explorer.environment.FunctionEnvironment3D;
import lombok.RequiredArgsConstructor;

/**
 * Created by andre on 03/07/16.
 */
@RequiredArgsConstructor
public class StandardFitnessFunction {

    private final boolean min;
    private final FunctionEnvironment3D environment;

    public double evaluate(int x, int y) {
        double result = 0.0d;
        try {
             result = environment.getCoordinates()[x][y][2];
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("lol");
        }
        if (min && Double.isInfinite(result)) {
            if (Double.compare(result, 0.0d) < 0) {
                return Double.POSITIVE_INFINITY;
            } else {
                return Double.NEGATIVE_INFINITY;
            }
        } else if (Double.isNaN(result)) {
            return result;
        } else {
            return min ? -result : result;
        }
    }

}
