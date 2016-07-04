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
    public static Chromossome[] cross(Chromossome c1, Chromossome c2, double mutationRate) {
        Chromossome[] children = new Chromossome[16];
        int velocities[][] = new int[16][2];

        velocities[0][0] = c1.getVelocity()[0];
        velocities[0][1] = c2.getVelocity()[0];
        velocities[1][0] = c1.getVelocity()[0];
        velocities[1][1] = c2.getVelocity()[1];
        velocities[2][0] = c1.getVelocity()[1];
        velocities[2][1] = c2.getVelocity()[0];
        velocities[3][0] = c1.getVelocity()[1];
        velocities[3][1] = c2.getVelocity()[1];

        velocities[4][0] = c1.getVelocity()[0] + c2.getVelocity()[0];
        velocities[4][1] = c2.getVelocity()[0] - c1.getVelocity()[0];
        velocities[5][0] = c1.getVelocity()[0] + c2.getVelocity()[1];
        velocities[5][1] = c2.getVelocity()[1] - c1.getVelocity()[0];
        velocities[6][0] = c1.getVelocity()[1] + c2.getVelocity()[0];
        velocities[6][1] = c2.getVelocity()[0] - c1.getVelocity()[1];
        velocities[7][0] = c1.getVelocity()[1] + c2.getVelocity()[1];
        velocities[7][1] = c2.getVelocity()[1] - c1.getVelocity()[1];

        for (int i = 0; i < 4; i++) {
            children[i] = new Chromossome(c1.getX(), c1.getY(), velocities[i]);
            if (rand.nextDouble() < mutationRate) {
                children[i].getVelocity()[i % 2] = GeneticsRunner.randMinusOne() * children[i].getVelocity()[i % 2] * rand.nextInt(2);
            }
        }
        for (int i = 4; i < 8; i++) {
            children[i] = new Chromossome(c2.getX(), c2.getY(), velocities[i - 4]);
            if (rand.nextDouble() < mutationRate) {
                children[i].getVelocity()[i % 2] = GeneticsRunner.randMinusOne() * children[i].getVelocity()[i % 2] * (rand.nextInt(2) + 1);
            }
        }
        for (int i = 8; i < 12; i++) {
            children[i] = new Chromossome(c2.getX(), c2.getY(), velocities[i - 8]);
            if (rand.nextDouble() < mutationRate) {
                children[i].getVelocity()[i % 2] = GeneticsRunner.randMinusOne() * children[i].getVelocity()[i % 2] * (rand.nextInt(2) + 1);
            }
        }
        for (int i = 12; i < 16; i++) {
            children[i] = new Chromossome(c2.getX(), c2.getY(), velocities[i - 12]);
            if (rand.nextDouble() < mutationRate) {
                children[i].getVelocity()[i % 2] = GeneticsRunner.randMinusOne() * children[i].getVelocity()[i % 2] * (rand.nextInt(2) + 1);
            }
        }
        return children;
    }
}
