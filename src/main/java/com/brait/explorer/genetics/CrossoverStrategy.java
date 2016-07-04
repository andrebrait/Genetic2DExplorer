package com.brait.explorer.genetics;

import com.brait.explorer.Main;
import com.brait.explorer.runner.GeneticsRunner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;

import static com.brait.explorer.Main.rand;

/**
 * Created by andre on 04/07/16.
 */
public class CrossoverStrategy {

    @SuppressWarnings("unchecked")
    public static Chromossome[] cross(Chromossome c1, Chromossome c2, double mutationRate, int xLen, int yLen) {
        Chromossome[] children = new Chromossome[8];
        int velocities[][] = new int[8][2];

        velocities[0][0] = c1.getXVel();
        velocities[0][1] = c2.getXVel();
        velocities[1][0] = c1.getXVel();
        velocities[1][1] = c2.getYVel();
        velocities[2][0] = c1.getYVel();
        velocities[2][1] = c2.getXVel();
        velocities[3][0] = c1.getYVel();
        velocities[3][1] = c2.getYVel();

        int index, xVel, yVel;
        for (int i = 0; i < 4; i++) {
            index = i;
            xVel = velocities[index][0];
            yVel = velocities[index][1];
            if (rand.nextDouble() < mutationRate) {
                if (i % 2 == 0) {
                    xVel = GeneticsRunner.randMinusOne() * xVel * (rand.nextInt(2) + 1);
                } else {
                    yVel = GeneticsRunner.randMinusOne() * yVel * (rand.nextInt(2) + 1);
                }
            }
            children[i] = new Chromossome(c1.getX(), c1.getY(), xVel, yVel);
        }
        for (int i = 4; i < 8; i++) {
            index = i - 4;
            xVel = velocities[index][0];
            yVel = velocities[index][1];
            if (rand.nextDouble() < mutationRate) {
                if (i % 2 == 0) {
                    xVel = GeneticsRunner.randMinusOne() * xVel * (rand.nextInt(2) + 1);
                } else {
                    yVel = GeneticsRunner.randMinusOne() * yVel * (rand.nextInt(2) + 1);
                }
            }
            children[i] = new Chromossome(c2.getX(), c2.getY(), xVel, yVel);
        }
        return children;
    }
}
