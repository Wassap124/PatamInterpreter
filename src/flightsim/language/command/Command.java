package flightsim.language.command;

import java.util.List;

public interface Command
{
    int execute(List<String> words);
    String getName();
}
