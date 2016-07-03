package com.brait.explorer;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;

public class Main {

    private static final Options options = new Options();
    private static final String fn_name = "fn", from_name = "from", to_name = "to", step_name = "step",
            min_name = "min";

    static {
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
            String step = StringUtils.deleteWhitespace(cmd.getOptionValue(step_name));
            Boolean min = cmd.hasOption(min_name);

            Expression function = new ExpressionBuilder(fn).variables("x", "y").build();

            Double[] xLimits = new Double[]{Double.parseDouble(from[0]), Double.parseDouble(to[0])};
            Double[] yLimits = new Double[]{Double.parseDouble(from[1]), Double.parseDouble(to[1])};

            int maxLen = xLimits.length + yLimits.length - 2;
            StringBuilder sb = new StringBuilder();
            sb.append("Function: z=").append(fn).append(".").append(System.lineSeparator()).append("Corner values: ");
            for (int i = 0; i < xLimits.length; i++) {
                for (int j = 0; j < yLimits.length; j++) {
                    Double x = xLimits[i];
                    Double y = yLimits[j];
                    sb.append("[x=").append(x).append(";y=").append(y).append("] z=");
                    sb.append(function.setVariable("x", x).setVariable("y", y).evaluate());
                    if(i+j < maxLen){
                        sb.append(", ");
                    }
                }
            }
            sb.append(".");
            System.out.println(sb.toString());

        } catch (MissingOptionException e) {
            System.out.println("\nAs seguintes opções estão faltado: " + StringUtils.join(e.getMissingOptions(), ", ") + ".\n");
            HelpFormatter help = new HelpFormatter();
            help.printHelp("java -jar <nome do jar>", options);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
