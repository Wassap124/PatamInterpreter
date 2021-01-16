package flightsim.language.command;

import flightsim.Environment;

import java.util.List;

public class DisconnectCommand implements Command
{
    public static final String CommandName="disconnect";
    private final Environment env;

    public DisconnectCommand(Environment env)
    {
        this.env = env;
    }

    @Override
    public int execute(List<String> arguments)
    {
        env.getClient().sendLine("bye");
        env.getClient().close();
        return 2;
    }

    @Override
    public String getName() {
        return CommandName;
    }
}
