package com.brait.explorer;

import com.brait.explorer.environment.FunctionEnvironment3D;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;

public class Main {

    private static final Options options = new Options();
    private static final String fn_name = "fn", from_name = "from", to_name = "to", step_name = "step",
            min_name = "min", n_name = "n", m_name = "m", c_name = "c", mode_name = "mode", ngen_name = "ngen";

    static {
        Option ngen = new Option(ngen_name, true, "Número de gerações a serem utilizadas (default: 1000)");
        ngen.setArgs(1);
        ngen.setRequired(true);
        options.addOption(ngen);

        Option fn = new Option(fn_name, true, "Função para avaliação em 3 dimensões (escrever como seria a função em Java, podendo utilizar símbolos). Ex.: -fn x^2+y resulta na função z=x²+y. O valor de z será o utilizado na busca.");
        fn.setArgs(1);
        fn.setRequired(true);
        options.addOption(fn);

        Option from = new Option(from_name, true, "Valores de X e Y mínimos para a avaliação da função. Ex.: -from 1.3 3.4 fará a avaliação a partir de x=1.3 e y=3.4");
        from.setArgs(2);
        from.setRequired(true);
        options.addOption(from);

        Option step = new Option(step_name, true, "Passo dado a cada variação de X e Y. Ex.: -step 0.1 fará com que se ande de 0.1 em 0.1 para X e Y");
        step.setArgs(1);
        step.setRequired(true);
        options.addOption(step);

        Option to = new Option(to_name, true, "Valores de X e Y máximos para a avaliação da função. Ex.: -to 100 100 fará a avaliação até x=100 e y=100");
        to.setArgs(2);
        to.setRequired(true);
        options.addOption(to);

        Option n = new Option(n_name, true, "Número de indivíduos na população (default: 100)");
        n.setArgs(1);
        options.addOption(n);

        Option m = new Option(m_name, true, "Taxa de mutação (entre 0 e 1) (default: 0.05)");
        m.setArgs(1);
        options.addOption(m);

        Option c = new Option(c_name, true, "Taxa de cruzamento (entre 0 e 1) (default: 0.60)");
        c.setArgs(1);
        options.addOption(c);

        Option mode = new Option(mode_name, true, "Modo de distribuição da população inicial na função. Pode ser random ou uniform (default: uniform)");
        mode.setArgs(1);
        options.addOption(mode);

        Option min = new Option(min_name, false, "Determina que se deve buscar os mínimos da função.");
        options.addOption(min);
    }

    public static void main(String[] args) {

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            String fn = StringUtils.deleteWhitespace(cmd.getOptionValue(fn_name));
            String[] from = cmd.getOptionValues(from_name);
            String[] to = cmd.getOptionValues(to_name);
            String stepStr = StringUtils.deleteWhitespace(cmd.getOptionValue(step_name));
            int ngen = cmd.hasOption(ngen_name) ? Integer.parseInt(cmd.getOptionValue(ngen_name)) : 1000;
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

            environment.generateIndexes(n, mode);


        } catch (MissingOptionException e) {
            System.out.println("\nAs seguintes opções estão faltado: " + StringUtils.join(e.getMissingOptions(), ", ") + ".\n");
            HelpFormatter help = new HelpFormatter();
            help.printHelp("java -jar <nome do jar>", options);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
