package flightsim.language.interpreter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lexer
{
    public static final String EOL ="eol";

    public static List<String> Lexer(InputStreamReader streamReader)
    {
        List<String> words =new ArrayList<>();
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        try
        {
            while((line = reader.readLine())!=null){
                Scanner scanner=new Scanner(line);
                while(scanner.hasNext())
                {
                    String token = scanner.next();
                    if (!token.startsWith("\""))
                    {
                        String[] splittedByMathChars = token.split("(?<=[-+*/()=])|(?=[-+*/()=])");

                        for (int i = 0; i < splittedByMathChars.length; i++) {
                            String word = splittedByMathChars[i];
                            if (word.equals("<")||word.equals("=")||word.equals(">")||word.equals("!")){
                                if(i<splittedByMathChars.length-1 && splittedByMathChars[i+1].equals("=")){
                                    words.add(word+"=");
                                    i++;
                                    continue;
                                }
                            }
                            words.add(word);
                        }
                    }
                    else
                    {
                        words.add(token);
                    }
                }
                words.add(EOL);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return words;
    }

    public static List<String> Lexer(String script)
    {
        return Lexer(new InputStreamReader(new ByteArrayInputStream(script.getBytes())));
    }

    public static List<String> Lexer(String[] script)
    {
        List<String> list =new ArrayList<>();
        for (String line : script)
        {
            list.addAll(Lexer.Lexer(line));
        }
        return list;
    }
}
