package com.brait.explorer.genetics;

/**
 * Created by andre on 04/07/16.
 */
public class CrossoverStrategy {

    public static Chromossome[] cross(Chromossome c1, Chromossome c2) {
        Chromossome[] children = new Chromossome[6];
        int velocities[][] = new int[6][2];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                velocities[2 * i + j][j] = c1.getVelocity()[i][j];
            }
        }
    }

}
