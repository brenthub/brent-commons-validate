package cn.brent.commons.validate;

import java.io.Serializable;
import java.lang.reflect.Field;

public class ValContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1534250961775382232L;

	protected final Field field;

	public ValContext(final Field field) {
		this.field = field;
	}

	@Override
	public String toString() {
		return field.getDeclaringClass().getName() + "." + field.getName();
	}

	public Field getField() {
		return field;
	}

}
