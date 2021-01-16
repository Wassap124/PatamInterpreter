package flightsim.language.command;

import flightsim.Environment;
import flightsim.client.SimpleClient;

import java.util.List;

public class ConnectToClientCommand implements Command
{
    public static final String CommandName="connect";
    private final Environment env;

    public ConnectToClientCommand(Environment env)
    {
        this.env = env;
    }

    @Override
    public int execute(List<String> arguments)
    {
        env.setClient(new SimpleClient());
        env.getClient().connect(arguments.get(1),Integer.parseInt(arguments.get(2)));
        return 4;
    }

    @Override
    public String getName() {
        return CommandName;
    }


}
