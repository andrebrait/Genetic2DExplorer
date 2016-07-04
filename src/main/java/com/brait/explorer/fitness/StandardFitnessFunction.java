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

    public double evaluate(int x, int y){
        if(x < 0 || y < 0 || x > environment.getXLen() || y > environment.getYLen()){
            return min ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        }
        return min ? -environment.getCoordinates()[x][y][2] : environment.getCoordinates()[x][y][2];
    }

}
