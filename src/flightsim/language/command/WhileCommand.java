package flightsim.language.command;

import flightsim.Environment;
import flightsim.language.Condition;
import flightsim.language.interpreter.Lexer;

import java.util.ArrayList;
import java.util.List;

public class WhileCommand implements Command
{
    public static final String CommandName="while";
    private final Environment env;

    public WhileCommand(Environment env) {
        this.env = env;
    }

    @Override
    public int execute(List<String> args)
    {
        Condition condition = new Condition(env, args);

        int condsize =args.indexOf("{");
        List<String> block = args.subList(args.indexOf(Lexer.EOL)+1, args.indexOf("}"));
        while (condition.evaluate()) {
            env.getParser().parse(block);
        }

        return 1 + condsize + 1 + block.size() + 1+1;// while cond { \n block } \n
    }

    private int findEndOfBlock(List<String> args) {
        return args.indexOf("}"); //TODO: check inner loop in loop
    }

    @Override
    public String getName() {
        return CommandName;
    }


}