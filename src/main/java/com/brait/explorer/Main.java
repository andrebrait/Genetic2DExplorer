package com.brait.explorer;

import com.brait.explorer.environment.FunctionEnvironment3D;
import com.brait.explorer.runner.GeneticsRunner;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;

import java.util.Random;

public class Main {

    private static final Options options = new Options();
    public static final Random rand = new Random();

    static {
        Option ngen = new Option(GeneticsRunner.ngen_name, true, "Número de gerações a serem utilizadas (default: 1000)");
        ngen.setArgs(1);
        ngen.setRequired(true);
        options.addOption(ngen);

        Option fn = new Option(GeneticsRunner.fn_name, true, "Função para avaliação em 3 dimensões (escrever como seria a função em Java, podendo utilizar símbolos). Ex.: -fn x^2+y resulta na função z=x²+y. O valor de z será o utilizado na busca.");
        fn.setArgs(1);
        fn.setRequired(true);
        options.addOption(fn);

        Option from = new Option(GeneticsRunner.from_name, true, "Valores de X e Y mínimos para a avaliação da função. Ex.: -from 1.3 3.4 fará a avaliação a partir de x=1.3 e y=3.4");
        from.setArgs(2);
        from.setRequired(true);
        options.addOption(from);

        Option step = new Option(GeneticsRunner.step_name, true, "Passo dado a cada variação de X e Y. Ex.: -step 0.1 fará com que se ande de 0.1 em 0.1 para X e Y");
        step.setArgs(1);
        step.setRequired(true);
        options.addOption(step);

        Option to = new Option(GeneticsRunner.to_name, true, "Valores de X e Y máximos para a avaliação da função. Ex.: -to 100 100 fará a avaliação até x=100 e y=100");
        to.setArgs(2);
        to.setRequired(true);
        options.addOption(to);

        Option n = new Option(GeneticsRunner.n_name, true, "Número de indivíduos na população (default: 100)");
        n.setArgs(1);
        options.addOption(n);

        Option m = new Option(GeneticsRunner.m_name, true, "Taxa de mutação (entre 0 e 1) (default: 0.05)");
        m.setArgs(1);
        options.addOption(m);

        Option c = new Option(GeneticsRunner.c_name, true, "Taxa de cruzamento (entre 0 e 1) (default: 0.60)");
        c.setArgs(1);
        options.addOption(c);

        Option mode = new Option(GeneticsRunner.mode_name, true, "Modo de distribuição da população inicial na função. Pode ser random ou uniform (default: uniform)");
        mode.setArgs(1);
        options.addOption(mode);

        Option min = new Option(GeneticsRunner.min_name, false, "Determina que se deve buscar os mínimos da função.");
        options.addOption(min);

        Option out = new Option(GeneticsRunner.out_name, true, "Determina a pasta para a saída dos arquivos, se alguma");
        out.setArgs(1);
        options.addOption(out);
    }

    public static void main(String[] args) {

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            GeneticsRunner.run(cmd);
        } catch (MissingOptionException e) {
            System.out.println("\nAs seguintes opções estão faltado: " + StringUtils.join(e.getMissingOptions(), ", ") + ".\n");
            HelpFormatter help = new HelpFormatter();
            help.printHelp("java -jar <nome do jar>", options);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
