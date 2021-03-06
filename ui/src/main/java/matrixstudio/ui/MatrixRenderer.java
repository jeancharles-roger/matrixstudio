package matrixstudio.ui;

import matrixstudio.model.Matrix;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;


/**
 * A {@link MatrixRenderer} is able to create an {@link Image} from a {@link Matrix}.
 */
public interface MatrixRenderer {

	void render(GC gc, RendererContext context, Matrix matrix, int mouseZ);
	void render3D(GC gc, RendererContext context, Matrix matrix, float angleY, float phi, float d_recul, float d_focale);
	
}
