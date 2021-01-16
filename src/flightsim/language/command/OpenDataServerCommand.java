package flightsim.language.command;

import flightsim.Environment;
import java.util.List;
import static java.lang.Integer.parseInt;

public class OpenDataServerCommand implements Command
{
    public static final String CommandName="openDataServer";
    private final Environment env;

    public OpenDataServerCommand(Environment env)
    {
        this.env = env;
    }

    @Override
    public int execute(List<String> args)
    {
        env.getServer().listen(parseInt(args.get(1)), parseInt(args.get(2)));
        return 4;
    }

    @Override
    public String getName()
    {
        return CommandName;
    }
}
