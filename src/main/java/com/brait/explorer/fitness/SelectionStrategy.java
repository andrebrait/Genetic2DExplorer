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

    public static Chromossome[] select(StandardFitnessFunction function, Chromossome[] population, double crossoverRate, double mutationRate, int n) {
        sort(function, population);

        int len = Integer.min(n, population.length);

        int tenth = len / 10;
        Chromossome[] best = new Chromossome[tenth];
        System.arraycopy(population, 0, best, 0, tenth);

        int percentile = (int) (crossoverRate * len);
        Chromossome[] selectedCrossOver = new Chromossome[percentile];
        for (int i = 0; i < percentile; i++) {
            for (int j = tenth + i; j < len; j++) {
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
        System.arraycopy(selectedCrossOver, 0, toCrossOver, tenth, total - tenth);

        List<Chromossome> toCrossList = Lists.newArrayList(toCrossOver);
        Collections.shuffle(toCrossList);
        toCrossOver = toCrossList.toArray(toCrossOver);

        int numCouples = toCrossOver.length / 2;
        int crossedOverLength = numCouples * 8;

        Chromossome[] crossedOver = new Chromossome[crossedOverLength];
        Chromossome[] children;
        for (int i = 0; i < numCouples * 2; i += 2) {
            children = CrossoverStrategy.cross(toCrossOver[i], toCrossOver[i + 1], mutationRate);
            System.arraycopy(children, 0, crossedOver, i / 2 * 8, 8);
        }

        Chromossome[] retVal = new Chromossome[tenth + crossedOverLength];
        System.arraycopy(best, 0, retVal, 0, tenth);
        System.arraycopy(crossedOver, 0, retVal, tenth, crossedOverLength);

        LinkedHashMap<Chromossome, int[]> count = new LinkedHashMap<>();
        for (int i = 0; i < retVal.length - 1; i++) {
            if (!count.containsKey(retVal[i])) {
                count.put(retVal[i], new int[]{0});
            }
            count.get(retVal[i])[0]++;
        }

        int index = 0;
        int nItems = 0;
        for(Map.Entry<Chromossome, int[]> entry : count.entrySet()){
             nItems += Integer.min(entry.getValue()[0], 16);
        }
        retVal = new Chromossome[nItems];
        for(Map.Entry<Chromossome, int[]> entry : count.entrySet()){
            nItems = Integer.min(entry.getValue()[0], 16);
            for(int i = 0; i < nItems; i++){
                retVal[index] = entry.getKey();
                index++;
            }
        }

        return retVal;

    }
}
