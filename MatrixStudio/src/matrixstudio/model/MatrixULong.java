package matrixstudio.model;

import org.xid.basics.serializer.BoostUtil;
import org.xid.basics.serializer.BoostObject;
import org.xid.basics.serializer.Boost;
import org.xid.basics.model.ModelObject;
import org.xid.basics.model.ChangeRecorder;
import matrixstudio.model.ModelVisitor;
import matrixstudio.model.Matrix;
import java.util.Random;


public class MatrixULong extends Matrix implements ModelObject, BoostObject {

	private long[] matrix;

	private long[] matrixInit;

	public MatrixULong() {
	}

	protected MatrixULong(Boost boost) {
		super(boost);
		matrix = BoostUtil.readLongArray(boost);
		matrixInit = BoostUtil.readLongArray(boost);
	}

	/**
	 * <p>Gets matrix.</p>
	 */
	public long[] getMatrix() {
		return matrix;
	}

	/**
	 * <p>Sets matrix.</p>
	 */
	public void setMatrix(long[] newValue) {
		if (matrix != newValue) {
			getChangeRecorder().recordChangeAttribute(this, "matrix", this.matrix);
			this.matrix= newValue;
		}
	}

	/**
	 * <p>Gets matrixInit.</p>
	 */
	public long[] getMatrixInit() {
		return matrixInit;
	}

	/**
	 * <p>Sets matrixInit.</p>
	 */
	public void setMatrixInit(long[] newValue) {
		if (matrixInit != newValue) {
			getChangeRecorder().recordChangeAttribute(this, "matrixInit", this.matrixInit);
			this.matrixInit= newValue;
		}
	}

	public void initBlank() {
		matrixInit = new long[getSizeX()*getSizeY()*getSizeZ()];
		matrix = new long[getSizeX()*getSizeY()*getSizeZ()];
		final Random random = new Random();
		if(isRandom()) {
			for ( int i=0; i< matrix.length; i++) { matrix[i] = random.nextLong(); matrixInit[i] = matrix[i];}
		} else {
			for ( int i=0; i< matrix.length; i++) { matrix[i] = 0l; matrixInit[i] = matrix[i];}
		}
	}

	public String getCType() {
		return "ulong *";
	}

	public void setToInitialValues() {
		for ( int i=0; i<matrix.length; i++ ) {
			matrix[i] = matrixInit[i];
		}
	}

	public Number getValueAt(int i, int j, int k) {
		if(i>=0 && j>=0 && k>=0 && i<getSizeX() && j<getSizeY() && k<getSizeZ()) {
			return matrix[i+getSizeX()*j+getSizeX()*getSizeY()*k];
		} else {
			return 0f;
		}
	}

	public void setValueAt(int i, int j, int k, Number v) {
		matrix[i+getSizeX()*j+getSizeX()*getSizeY()*k] = v.longValue();
	}

	public void setInitValueAt(int i, int j, int k, Number v) {
		matrixInit[i+getSizeX()*j+getSizeX()*getSizeY()*k] = v.longValue();
	}

	public void writeToBoost(Boost boost) {
		super.writeToBoost(boost);
		BoostUtil.writeLongArray(boost, matrix);
		BoostUtil.writeLongArray(boost, matrixInit);
	}

	/**
	 * Visitor accept method.
	 */
	public void accept(ModelVisitor visitor) {
		visitor.visitMatrixULong(this);
	}

	public ChangeRecorder getChangeRecorder() {
		if ( getModel() != null ) {
			return getModel().getChangeRecorder();
		}
		return super.getChangeRecorder();
	}

}

