package flightsim.language.command;

import flightsim.Environment;
import flightsim.language.interpreter.Lexer;
import flightsim.language.interpreter.calculator.ShuntingYard;

import java.util.List;

public class ReturnCommand implements Command
{
    public static final String CommandName="return";
    private final Environment env;

    public ReturnCommand(Environment env)
    {
        this.env = env;
    }

    @Override
    public int execute(List<String> arguments)
    {
        List<String> stringList = arguments.subList(1, arguments.indexOf(Lexer.EOL) );
        String collect = String.join("", stringList);
        Double value = ShuntingYard.calc(collect, env);
        env.setReturnValue((int)Math.floor(value));
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        return arguments.indexOf(Lexer.EOL)+1;
    }

    @Override
    public String getName() {
        return CommandName;
    }
}
