package com.brait.explorer.genetics;

import com.brait.explorer.runner.GeneticsRunner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * Created by andre on 04/07/16.
 */
public class CrossoverStrategy {

    @SuppressWarnings("unchecked")
    public static Chromossome[] cross(Chromossome c1, Chromossome c2, double mutationRate) {
        Random rand = new Random();
        Chromossome[] children = new Chromossome[8];
        int velocities[][] = new int[4][2];

        velocities[0][0] = c1.getVelocity()[0];
        velocities[0][1] = c2.getVelocity()[0];
        velocities[1][0] = c1.getVelocity()[0];
        velocities[1][1] = c2.getVelocity()[1];
        velocities[2][0] = c1.getVelocity()[1];
        velocities[2][1] = c2.getVelocity()[0];
        velocities[3][0] = c1.getVelocity()[1];
        velocities[3][1] = c2.getVelocity()[1];

        for (int i = 0; i < 4; i++) {
            children[i] = new Chromossome(c1.getX(), c1.getY(), velocities[i]);
            if (rand.nextDouble() < mutationRate) {
                children[i].getVelocity()[i % 2] = GeneticsRunner.randMinusOne() * velocities[i][i % 2] * rand.nextInt(10);
            }
        }
        for (int i = 4; i < 8; i++) {
            children[i] = new Chromossome(c2.getX(), c2.getY(), velocities[i - 4]);
            if (rand.nextDouble() < mutationRate) {
                children[i].getVelocity()[i % 2] = GeneticsRunner.randMinusOne() * velocities[i - 4][i % 2] * rand.nextInt(10);
            }
        }
        return children;
    }

}
