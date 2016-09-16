package matrixstudio.formula;

import matrixstudio.formula.BinaryOperation.Operation;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for formula parser
 */
public class FormulaParserTest {
    @Test
    public void parseReference() throws Exception {
        Formula result = new FormulaParser("result").parse();
        Assert.assertTrue(result.equals(new Reference("result")));

        result = new FormulaParser("otherName").parse();
        Assert.assertTrue(result.equals(new Reference("otherName")));
    }

    @Test
    public void parseLiteral() throws Exception {
        Formula result = new FormulaParser("123").parse();
        Assert.assertTrue(result.equals(new Literal(123l)));

        result = new FormulaParser("+4567").parse();
        Assert.assertTrue(result.equals(new Literal(4567l)));

        result = new FormulaParser("-8910").parse();
        Assert.assertTrue(result.equals(new Literal(-8910l)));
    }

    @Test
    public void parseSubFormula() throws Exception {
        Formula result = new FormulaParser("(123)").parse();
        Assert.assertTrue(result.equals(new SubFormula(new Literal(123l))));

        result = new FormulaParser("(otherName + 4 + a)").parse();
        Assert.assertTrue(result.equals(
            new SubFormula(
                new BinaryOperation(Operation.Plus,
                    new BinaryOperation(
                            Operation.Plus,
                            new Reference("otherName"),
                            new Literal(4l)
                        ),
                        new Reference("a")
                    )
                )
            )
        );


        result = new FormulaParser("(1-456)").parse();
        Assert.assertTrue(result.equals(new SubFormula(new BinaryOperation(Operation.Minus, new Literal(1l),new Literal(456l)))));

        result = new FormulaParser("(1 - (a + b))").parse();
        Assert.assertTrue(result.equals(
            new SubFormula(
                new BinaryOperation(Operation.Minus,
                    new Literal(1l),
                    new SubFormula(
                        new BinaryOperation(
                            Operation.Plus,
                            new Reference("a"),
                            new Reference("b")
                        )
                    )
                )
            )
        ));
    }

    @Test
    public void parseBinary() throws Exception {
        Formula result = new FormulaParser("1 + a").parse();
        Assert.assertTrue(result.equals(new BinaryOperation(Operation.Plus, new Literal(1l), new Reference("a"))));

        result = new FormulaParser("1 - a").parse();
        Assert.assertTrue(result.equals(new BinaryOperation(Operation.Minus, new Literal(1l), new Reference("a"))));

        result = new FormulaParser("1 * a").parse();
        Assert.assertTrue(result.equals(new BinaryOperation(Operation.Multiply, new Literal(1l), new Reference("a"))));

        result = new FormulaParser("1 / a").parse();
        Assert.assertTrue(result.equals(new BinaryOperation(Operation.Divide, new Literal(1l), new Reference("a"))));

        result = new FormulaParser("1 % a").parse();
        Assert.assertTrue(result.equals(new BinaryOperation(Operation.Modulus, new Literal(1l), new Reference("a"))));

        // left to right for parsing
        result = new FormulaParser("1 + a + b").parse();
        Assert.assertTrue(result.equals(
            new BinaryOperation(
                Operation.Plus,
                new BinaryOperation(
                    Operation.Plus,
                    new Literal(1l),
                    new Reference("a")
                ),
                new Reference("b")
            )
        ));
    }

    @Test
    public void parseBinaryWithPriorities() throws Exception {
        Formula result = new FormulaParser("1 + 2 * 3").parse();
        Assert.assertTrue(result.equals(
            new BinaryOperation(
                Operation.Plus,
                new Literal(1l),
                new BinaryOperation(
                    Operation.Multiply,
                    new Literal(2l),
                    new Literal(3l)
                )
            )
        ));

        result = new FormulaParser("1 * 2 - 3").parse();
        Assert.assertTrue(result.equals(
                new BinaryOperation(
                        Operation.Minus,
                        new BinaryOperation(
                                Operation.Multiply,
                                new Literal(1l),
                                new Literal(2l)
                        ),
                        new Literal(3l)
                )
        ));

        result = new FormulaParser("1 + 2 * 2 - 3").parse();
        Assert.assertTrue(result.equals(
            new BinaryOperation(
                Operation.Minus,
                new BinaryOperation(
                    Operation.Plus,
                    new Literal(1l),
                    new BinaryOperation(
                            Operation.Multiply,
                            new Literal(2l),
                            new Literal(2l)
                    )
                ),
                new Literal(3l)
            )
        ));
    }

}