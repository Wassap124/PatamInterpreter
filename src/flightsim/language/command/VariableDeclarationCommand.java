package flightsim.language.command;

import flightsim.Environment;
import flightsim.language.interpreter.Lexer;
import flightsim.language.interpreter.calculator.ShuntingYard;

import java.util.List;

public class VariableDeclarationCommand implements Command {

    public static final String CommandName="var";
    private final Environment env;

    public VariableDeclarationCommand(Environment env) {
        this.env = env;
    }


    @Override
    public int execute(List<String> arguments)
    {
        int n=0;
        if(arguments.get(0).equals("var"))
            n++;
        if (arguments.get(1+n).equals(Lexer.EOL))
            return n+2;
        if (arguments.get(2+n).equals("bind"))
        {
            String key = arguments.get(0 + n);
            String path = arguments.get(3+n);

            if (path.startsWith("\"") && path.endsWith("\""))
                path =path.substring(path.indexOf("\"")+1,path.lastIndexOf("\""));
            env.addBind(key,path);
            return 5+n;
        }
        else
            {
                String key =arguments.get(0+n);
                List<String> stringList = arguments.subList(2 + n, arguments.indexOf(Lexer.EOL) );
                String collect = String.join("", stringList);
                Double value = ShuntingYard.calc(collect, env);
                env.setValue(key,value);
                return 3+n+stringList.size();
            }
    }

    @Override
    public String getName() {
        return CommandName;
    }
}
