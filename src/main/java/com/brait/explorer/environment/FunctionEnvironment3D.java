package com.brait.explorer.environment;

import lombok.*;
import net.objecthunter.exp4j.Expression;
import org.apache.commons.math3.util.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by andre on 03/07/16.
 */
@RequiredArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
public class FunctionEnvironment3D {

    private final Expression expression;
    private double[][][] coordinates;

    private double xMin, xMax, yMin, yMax, delta;
    private int xLen, yLen;

    public static final int MODE_RANDOM = 0, MODE_UNIFORM = 1;

    public void init(double[] xLimits, double[] yLimits, double step) {
        delta = step;
        xMin = Double.min(xLimits[0], xLimits[1]);
        xMax = Double.max(xLimits[0], xLimits[1]);
        yMin = Double.min(yLimits[0], yLimits[1]);
        yMax = Double.max(yLimits[0], yLimits[1]);
        xLen = Double.valueOf((xMax - xMin) / step).intValue();
        yLen = Double.valueOf((yMax - yMin) / step).intValue();

        double x, y;

        coordinates = new double[xLen][yLen][3];

        x = xMin;
        for (int i = 0; i < xLen; i++) {
            y = yMin;
            for (int j = 0; j < yLen; j++) {
                expression.setVariable("x", x);
                expression.setVariable("y", y);
                coordinates[i][j][0] = x;
                coordinates[i][j][1] = y;
                coordinates[i][j][2] = expression.evaluate();
                y += step;
            }
            x += step;
        }
    }

    public int[][] generateIndexes(int n, int mode) {
        final int[][] retVal = new int[n][2];
        switch (mode) {
            case MODE_UNIFORM:

                int numSquaresTotal = (xLen * yLen) / n;
                int sqrtN = (int) Math.sqrt(numSquaresTotal);

                int numSquaresX = xLen / sqrtN;
                int sobraSquaresX = xLen % sqrtN;

                int numSquaresY = yLen / sqrtN;
                int sobraSquaresY = yLen % sqrtN;

                if (numSquaresX == 0) {
                    numSquaresX++;
                }

                if (numSquaresY == 0) {
                    numSquaresY++;
                }

                int dimX = xLen / (numSquaresX + sobraSquaresX);
                int distMedianX = dimX / 2;
                int dimY = yLen / (numSquaresY + sobraSquaresY);
                int distMedianY = dimY / 2;

                int medianX, medianY, count = 0;
                for (int i = 0; i < xLen; i += dimX) {
                    for (int j = 0; j < yLen && count < n; j += dimY) {
                        medianX = i + distMedianX;
                        medianY = j + distMedianY;
                        retVal[count][0] = medianX;
                        retVal[count][1] = medianY;
                        count++;
                    }
                }

                break;
            case MODE_RANDOM:
                Random rand = new Random();
                int[] xs = rand.ints(n, 0, xLen).toArray();
                int[] ys = rand.ints(n, 0, yLen).toArray();
                for (int i = 0; i < n; i++) {
                    retVal[i][0] = xs[i];
                    retVal[i][1] = ys[i];
                }
                break;
        }
        return retVal;
    }
}
