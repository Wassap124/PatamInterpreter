package flightsim.language.interpreter.calculator;

public class Plus extends BinaryExpression
{
    public Plus(Expression left, Expression right)
    {
        super(left, right);
    }

    @Override
    public Double calculate()
    {
        return left.calculate()+right.calculate();
    }

    @Override
    public String toString()
    {
        StringBuilder builder=new StringBuilder();
        if (left != null)
            builder.append(left.toString());
        if (right != null)
            builder.append(right.toString());
        return builder.toString()+"+";
    }
}
