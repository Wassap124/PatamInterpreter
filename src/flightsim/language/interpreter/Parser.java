package flightsim.language.interpreter;


import flightsim.Environment;
import flightsim.language.command.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class Parser
{
    private final Map<String, Command> commands;
    private final Environment env;
    private volatile boolean isStop;
    Thread parserthread=null;
    volatile List<String> words;
    public Parser(Environment env)
    {
        isStop=false;
        this.env = env;

        commands = Stream.of(
                new PrintCommand(env),
                new ConnectToClientCommand(env),
                new OpenDataServerCommand(env),
                new VariableDeclarationCommand(env),
                new SleepCommand(env),
                new WhileCommand(env),
                new ReturnCommand(env),
                new DisconnectCommand(env)
        ).collect(toMap(Command::getName, identity()));
    }

    public void threadparse(final List<String> wordsfinal){
        if (parserthread!=null){
            try {
                parserthread.stop();
            }catch (SecurityException ignored){}
        }
        parserthread=new Thread(() -> {
            env.closeAll();
            parse(wordsfinal);
        });
        if (!isStop)
            parserthread.start();
    }

    public void parse(List<String> words)
    {

        while (!words.isEmpty() && !isStop) {
            Command command = commands.getOrDefault(words.get(0), commands.get(VariableDeclarationCommand.CommandName));
            int numOfArgs = command.execute(words);
            words = words.subList(numOfArgs, words.size());
        }
    }

    public void addCommand(Command cmd)
    {
        commands.put(cmd.getName(), cmd);
    }

    public void stop(){
        isStop=true;
        if (parserthread!=null && parserthread.isAlive()) {
                parserthread.suspend();
        }
    }

    public void Resume(){
        isStop=false;
        if (parserthread!=null) {
            try{
                parserthread.start();
            }catch (IllegalThreadStateException e){
                try{
                    parserthread.resume();
                }catch (SecurityException e1){
                    e1.printStackTrace();
                }
            }
        }

    }
    public boolean isStop(){
        return isStop;
    }
}