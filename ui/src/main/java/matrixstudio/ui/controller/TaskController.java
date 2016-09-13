package matrixstudio.ui.controller;

import matrixstudio.model.Kernel;
import matrixstudio.model.Task;
import org.xid.basics.error.Diagnostic;
import org.xid.basics.error.Validator;
import org.xid.basics.ui.BasicsUI;
import org.xid.basics.ui.controller.Controller;
import org.xid.basics.ui.field.CompositeField;
import org.xid.basics.ui.field.Field;
import org.xid.basics.ui.field.ListField;
import org.xid.basics.ui.field.TextField;

import java.util.Arrays;
import java.util.List;


public class TaskController extends Controller<Task> {

	private ListField<Kernel> kernelField;

	private TextField positionField;
	private TextField globalWorkSizeXField;
	private TextField globalWorkSizeYField;
	private TextField globalWorkSizeZField;

	private CompositeField compositeField;
	
	@Override
	public CompositeField createFields() {
		kernelField = new ListField<Kernel>("Kernels", BasicsUI.NONE) {
			@Override
			public String getText(Kernel element) {
				return element.getName();
			}
		};
		kernelField.setTooltip("Change the Task's kernel reference.");
		kernelField.setValidator(new Validator.Stub<List<Kernel>>(Diagnostic.ERROR, "Task's kernel list can't be empty.") {
			public boolean isValid(List<Kernel> value) {
				return value != null && value.size() > 0;
			}
		});
		
		globalWorkSizeXField = new TextField("Global work size X", BasicsUI.NONE);
		globalWorkSizeXField.setValidator(new Validator.Stub<String>(Diagnostic.ERROR, "Invalid size") {
			public boolean isValid(String value) {
				if ( value == null ) return false;
				return value.matches("[0-9][0-9]*");
			}
		});
		globalWorkSizeYField = new TextField("Global work size Y", BasicsUI.NONE);
		globalWorkSizeYField.setValidator(new Validator.Stub<String>(Diagnostic.ERROR, "Invalid size") {
			public boolean isValid(String value) {
				if ( value == null ) return false;
				return value.matches("[0-9][0-9]*");
			}
		});
		globalWorkSizeZField = new TextField("Global work size Z", BasicsUI.NONE);
		globalWorkSizeZField.setValidator(new Validator.Stub<String>(Diagnostic.ERROR, "Invalid size") {
			public boolean isValid(String value) {
				if ( value == null ) return false;
				return value.matches("[0-9][0-9]*");
			}
		});
		positionField = new TextField("Position", BasicsUI.READ_ONLY);
		
		compositeField = new CompositeField("Task", BasicsUI.GROUP, kernelField, globalWorkSizeXField, globalWorkSizeYField, globalWorkSizeZField);
		return compositeField;
		
	}

	
	@Override
	public void refreshFields() {
		if ( getSubject() == null ) {
			compositeField.setEnable(false);
		} else {
			compositeField.setEnable(true);

            kernelField.setValue(getSubject().getKernelList());

			globalWorkSizeXField.setValue(""+getSubject().getGlobalWorkSizeX());
			globalWorkSizeYField.setValue(""+getSubject().getGlobalWorkSizeY());
			globalWorkSizeZField.setValue(""+getSubject().getGlobalWorkSizeZ());
			StringBuilder position = new StringBuilder();
			position.append(Arrays.toString(getSubject().getPosition()));
			positionField.setValue(position.toString());
		}
	}
	
	@Override
	public boolean updateSubject(Field field) {
		if ( field == globalWorkSizeXField ) {
			getSubject().setGlobalWorkSizeX(globalWorkSizeXField.getIntValue());
			return true;
		}
		if ( field == globalWorkSizeYField ) {
			getSubject().setGlobalWorkSizeY(globalWorkSizeYField.getIntValue());
			return true;
		}
		if ( field == globalWorkSizeZField ) {
			getSubject().setGlobalWorkSizeZ(globalWorkSizeZField.getIntValue());
			return true;
		}
		return super.updateSubject(field);
	}
}
