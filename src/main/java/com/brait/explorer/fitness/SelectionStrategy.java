package com.brait.explorer.fitness;

import com.brait.explorer.genetics.Chromossome;
import com.brait.explorer.genetics.CrossoverStrategy;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static com.brait.explorer.Main.rand;

/**
 * Created by andre on 04/07/16.
 */
public class SelectionStrategy {

    @RequiredArgsConstructor
    private static class CompareChromossome implements Comparator<Chromossome> {

        private final StandardFitnessFunction function;

        @Override
        public int compare(Chromossome o1, Chromossome o2) {
            double result1 = function.evaluate(o1.getX(), o1.getY());
            double result2 = function.evaluate(o2.getX(), o2.getY());
            return -Double.compare(result1, result2);
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }

    public static void sort(final StandardFitnessFunction function, final Chromossome[] population) {
        Arrays.sort(population, new CompareChromossome(function));
    }

    public static Chromossome[] select(StandardFitnessFunction function, Chromossome[] population, double crossoverRate, double mutationRate, int n, int xLen, int yLen) {
        sort(function, population);

        int tenth = population.length / 6;
        Chromossome[] best = new Chromossome[tenth];
        System.arraycopy(population, 0, best, 0, tenth);

        int percentile = (int) (crossoverRate * population.length);
        Chromossome[] selectedCrossOver = new Chromossome[percentile];
        for (int i = 0; i < percentile; i++) {
            for (int j = tenth + i; j < population.length; j++) {
                if (rand.nextDouble() < crossoverRate) {
                    selectedCrossOver[i] = population[j];
                }
            }
            if (selectedCrossOver[i] == null) {
                i--;
            }
        }

        int total = tenth + percentile;
        Chromossome[] toCrossOver = new Chromossome[total];
        System.arraycopy(best, 0, toCrossOver, 0, tenth);
        System.arraycopy(selectedCrossOver, 0, toCrossOver, tenth, percentile);

        List<Chromossome> toCrossList = Lists.newArrayList(toCrossOver);
        Collections.shuffle(toCrossList, rand);
        toCrossOver = toCrossList.toArray(toCrossOver);

        int numCouples = Integer.max(toCrossOver.length - 1, 1);
        int crossedOverLength = numCouples * 8;
        int count = 0;

        Chromossome[] crossedOver = new Chromossome[crossedOverLength];
        Chromossome[] children;
        for (int i = 0; i < numCouples; i++) {
            children = CrossoverStrategy.cross(toCrossOver[i], toCrossOver[i + 1], mutationRate, xLen, yLen);
            System.arraycopy(children, 0, crossedOver, count, children.length);
            count += children.length;
        }

        Set<Chromossome> resultSet = new HashSet<>(crossedOver.length + best.length);
        resultSet.addAll(Arrays.asList(crossedOver));
        resultSet.addAll(Arrays.asList(best));

        return resultSet.toArray(new Chromossome[]{});

    }
}
