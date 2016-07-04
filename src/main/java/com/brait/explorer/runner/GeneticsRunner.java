package com.brait.explorer.runner;

import com.brait.explorer.environment.FunctionEnvironment3D;
import com.brait.explorer.fitness.SelectionStrategy;
import com.brait.explorer.fitness.StandardFitnessFunction;
import com.brait.explorer.genetics.Chromossome;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.brait.explorer.Main.rand;

/**
 * Created by andre on 04/07/16.
 */
public class GeneticsRunner {

    public static final String fn_name = "fn", from_name = "from", to_name = "to", step_name = "step",
            min_name = "min", n_name = "n", m_name = "m", c_name = "c", mode_name = "mode", ngen_name = "ngen";

    public static int randMinusOne() {
        return (int) Math.pow(-1.0, rand.nextInt(5));
    }

    public static void run(CommandLine cmd) {
        String fn = StringUtils.deleteWhitespace(cmd.getOptionValue(fn_name));
        String[] from = cmd.getOptionValues(from_name);
        String[] to = cmd.getOptionValues(to_name);
        String stepStr = StringUtils.deleteWhitespace(cmd.getOptionValue(step_name));
        int ngen = cmd.hasOption(ngen_name) ? Integer.parseInt(cmd.getOptionValue(ngen_name)) : 100;
        int n = cmd.hasOption(n_name) ? Integer.parseInt(cmd.getOptionValue(n_name)) : 100;
        double m = cmd.hasOption(m_name) ? Double.parseDouble(cmd.getOptionValue(m_name)) : 0.05;
        double c = cmd.hasOption(c_name) ? Double.parseDouble(cmd.getOptionValue(c_name)) : 0.60;
        boolean min = cmd.hasOption(min_name);
        int mode = cmd.hasOption(mode_name) && cmd.getOptionValue(mode_name).equals("random") ? FunctionEnvironment3D.MODE_RANDOM : FunctionEnvironment3D.MODE_UNIFORM;

        Expression function = new ExpressionBuilder(fn).variables("x", "y").build();

        double[] xLimits = new double[]{Double.parseDouble(from[0]), Double.parseDouble(to[0])};
        double[] yLimits = new double[]{Double.parseDouble(from[1]), Double.parseDouble(to[1])};
        double step = Double.parseDouble(stepStr);

        FunctionEnvironment3D environment = new FunctionEnvironment3D(function);
        environment.init(xLimits, yLimits, step);
        int[][] indexes = environment.generateIndexes(n, mode);

        Chromossome[] initialPopulation = new Chromossome[n];
        for (int i = 0; i < n; i++) {
            initialPopulation[i] = new Chromossome(indexes[i][0], indexes[i][1], randMinusOne() * rand.nextInt(5), randMinusOne() * rand.nextInt(5));
        }

        StandardFitnessFunction stdFunc = new StandardFitnessFunction(min, environment);
        Chromossome[] newPopulation = initialPopulation;
        for (int gen = 0; gen < ngen; gen++) {
            for (Chromossome aNewPopulation : newPopulation) {
                for (int k = 0; k < rand.nextInt(5); k++) {
                    aNewPopulation.move(environment.getXLen(), environment.getYLen());
                }
            }
            newPopulation = SelectionStrategy.select(stdFunc, newPopulation, c, m, n, environment.getXLen(), environment.getYLen());
        }

        SelectionStrategy.sort(stdFunc, newPopulation);

        Set<Chromossome> results = new LinkedHashSet<>(Arrays.asList(newPopulation));

        for (Chromossome cr : results) {
            System.out.println(cr.toString() + " " + stdFunc.getCoordinates(cr.getX(), cr.getY()));
        }

    }

}
