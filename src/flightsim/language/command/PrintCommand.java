package flightsim.language.command;

import flightsim.Environment;

import java.util.List;

public class PrintCommand implements Command
{
    public static final String CommandName="print";
    private final Environment env;

    public PrintCommand(Environment env) {
        this.env = env;
    }

    @Override
    public int execute(List<String> arguments)
    {
        String word = arguments.get(1);
        if (word.startsWith("\"") && word.endsWith("\""))
            word =word.substring(word.indexOf("\"")+1,word.lastIndexOf("\""));
        else
            word = env.getValue(word)+"";
        System.out.println(word);
        return 3;
    }

    @Override
    public String getName() {
        return CommandName;
    }
}
