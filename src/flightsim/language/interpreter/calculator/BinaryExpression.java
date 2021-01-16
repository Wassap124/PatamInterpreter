package flightsim.language.interpreter.calculator;

public abstract class BinaryExpression implements Expression
{
    protected Expression left;
    protected Expression right;

    public BinaryExpression(Expression left, Expression right)
    {
        this.left = left;
        this.right = right;
    }

    public void setLeft(Expression left)
    {
        this.left = left;
    }

    public void setRight(Expression right)
    {
        this.right = right;
    }

}
