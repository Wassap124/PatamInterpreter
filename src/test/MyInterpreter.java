package test;

import flightsim.Environment;
import flightsim.client.SimpleClient;
import flightsim.language.interpreter.Lexer;
import flightsim.language.interpreter.Parser;
import flightsim.server.DataReaderServer;

import java.util.List;

public class MyInterpreter {

	public static  int interpret(String[] lines){
		// call your interpreter here
		Environment env = new Environment();
		Parser parser = new Parser(env);
		env.setParser(parser);
		env.setServer(new DataReaderServer(env));
		env.setClient(new SimpleClient());
		List<String> lexer = Lexer.Lexer(lines);
//		lexer.stream().forEach(s -> System.out.print(s+","));
//		System.out.println();
		parser.parse(lexer);
		int returnValue = env.getReturnValue();
		if (!env.getClient().isClose())
			env.getClient().close();
		env.getServer().stop();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
//			e.printStackTrace();
		}
		return returnValue;
	}
}
