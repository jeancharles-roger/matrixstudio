package matrixstudio.formula;

import java.util.Map;

/**
 * <p>Computable formula for parameters in MatrixStudio.</p>
 *
 * <a href="https://github.com/jeancharles-roger/matrixstudio/wiki/Parameters">Wiki page.</a>
 *
 */
public interface Formula {

    /** Evaluate formula with given context. */
    int evaluate(Map<String, Integer> context) throws EvaluationException;

}
