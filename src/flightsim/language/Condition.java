package flightsim.language;


import flightsim.Environment;
import flightsim.language.interpreter.Lexer;
import flightsim.language.interpreter.calculator.ShuntingYard;

import java.util.List;
import java.util.function.Predicate;

public class Condition
{
    Predicate<Void> stringPredicate;

    public Condition(Environment environment, List<String> arguments)
    {
        List<String> stringList = arguments.subList(1, arguments.indexOf(Lexer.EOL)-1 );

        int indexsplit;
        if ((indexsplit = stringList.indexOf(">="))!=-1)
        {
            int finalIndexsplit = indexsplit;
            stringPredicate = s -> {
                List<String> leftOperand=stringList.subList(0, finalIndexsplit );
                List<String> rightOperand=stringList.subList(finalIndexsplit +1,stringList.size());
                return ShuntingYard.calc(String.join("", leftOperand), environment) >= ShuntingYard.calc(String.join("", rightOperand), environment);
            };
        }
        else if ((indexsplit = stringList.indexOf("<="))!=-1)
        {
            int finalIndexsplit = indexsplit;
            stringPredicate = s -> {
                List<String> leftOperand=stringList.subList(0, finalIndexsplit );
                List<String> rightOperand=stringList.subList(finalIndexsplit +1,stringList.size());
                return ShuntingYard.calc(String.join("", leftOperand), environment) <= ShuntingYard.calc(String.join("", rightOperand), environment);
            };
        }
        else if ((indexsplit = stringList.indexOf("=="))!=-1)
        {
            int finalIndexsplit = indexsplit;
            stringPredicate = s -> {
                List<String> leftOperand=stringList.subList(0, finalIndexsplit );
                List<String> rightOperand=stringList.subList(finalIndexsplit +1,stringList.size());
                return ShuntingYard.calc(String.join("", leftOperand), environment) == ShuntingYard.calc(String.join("", rightOperand), environment);
            };
        }
        else if ((indexsplit = stringList.indexOf("!="))!=-1)
        {
            int finalIndexsplit = indexsplit;
            stringPredicate = s -> {
                List<String> leftOperand=stringList.subList(0, finalIndexsplit );
                List<String> rightOperand=stringList.subList(finalIndexsplit +1,stringList.size());
                return ShuntingYard.calc(String.join("", leftOperand), environment) != ShuntingYard.calc(String.join("", rightOperand), environment);
            };
        }
        else if ((indexsplit = stringList.indexOf(">"))!=-1)
        {
            int finalIndexsplit = indexsplit;
            stringPredicate = s -> {
                List<String> leftOperand=stringList.subList(0, finalIndexsplit );
                List<String> rightOperand=stringList.subList(finalIndexsplit +1,stringList.size());
                return ShuntingYard.calc(String.join("", leftOperand), environment) > ShuntingYard.calc(String.join("", rightOperand), environment);
            };
        }
        else if ((indexsplit = stringList.indexOf("<"))!=-1)
        {
            int finalIndexsplit = indexsplit;
            stringPredicate = s -> {
                List<String> leftOperand=stringList.subList(0, finalIndexsplit );
                List<String> rightOperand=stringList.subList(finalIndexsplit +1,stringList.size());
                return ShuntingYard.calc(String.join("", leftOperand), environment) < ShuntingYard.calc(String.join("", rightOperand), environment);
            };
        }
        else
            throw new RuntimeException("no operators founds");// TODO check in the future about while true or false
    }

    public boolean evaluate()
    {
        return stringPredicate.test(null);
    }
}
