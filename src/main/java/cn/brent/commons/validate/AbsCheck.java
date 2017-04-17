package cn.brent.commons.validate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.brent.commons.validate.expression.ExpressionLanguageFactory;
import cn.brent.commons.validate.expression.IExpressionLanguage;

public abstract class AbsCheck implements Check {
	
	protected Logger logger=LoggerFactory.getLogger(getClass());
	
	private Field field;

	private String errorCode;
	private String message;
	private Map<String, ? extends Serializable> messageVariables;

	/** 获取占位变量标志位*/
	private boolean messageVariablesFlag = false;
	
	private String[] profiles;
	private int severity;
	
	private ConstraintTarget[] appliesTo;
	
	private String target;
	/** 表达式语言:表达式 */
	private String when;
	/** 表达式 */
	private transient String whenFormula;
	/** 表达式语言 */
	private transient String whenLang;
	
	@Override
	public ConstraintTarget[] getAppliesTo() {
		if (appliesTo == null) {
			return new ConstraintTarget[] { ConstraintTarget.CONTAINER };
		}
		return appliesTo;
	}

	@Override
	public void setAppliesTo(ConstraintTarget... appliesTo) {
		this.appliesTo = appliesTo;
	}

	@Override
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String[] getProfiles() {
		return profiles;
	}

	@Override
	public void setProfiles(String... profiles) {
		this.profiles = profiles;
	}

	@Override
	public int getSeverity() {
		return severity;
	}

	@Override
	public void setSeverity(int severity) {
		this.severity = severity;
	}

	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String getWhen() {
		return when;
	}
	
	@Override
	public void setField(Field field) {
		this.field=field;
	}
	
	@Override
	public Field getField() {
		return this.field;
	}

	@Override
	public void setWhen(String when) {
		synchronized (this) {
			if (when == null || when.length() == 0) {
				this.when = null;
				whenFormula = null;
				whenLang = null;
			} else {
				final String[] parts = when.split(":", 2);
				if (parts.length == 0) {
					throw new IllegalArgumentException("[when] is missing the scripting language declaration");
				}
				this.when = when;
				if(parts.length==1){
					whenLang = "";
					whenFormula = parts[0];
				}else{
					whenLang = parts[0];
					whenFormula = parts[1];
				}
			}
		}
	}

	@Override
	public boolean isActive(Object validatedObject, Object valueToValidate) {

		if (when == null) {
			return true;
		}

		if (whenLang == null) {
			setWhen(when);
		}

		final Map<String, Object> values = new HashMap<String, Object>();
		values.put("_value", valueToValidate);
		values.put("_this", validatedObject);

		final IExpressionLanguage el = ExpressionLanguageFactory.get(whenLang);
		return el.evaluateAsBoolean(whenFormula, values);
	}

	@Override
	public Map<String, ? extends Serializable> getMessageVariables() {
		if(!messageVariablesFlag){
			messageVariables=createMessageVariables();
			messageVariablesFlag=true;
		}
		return messageVariables;
	}

	/**
	 * 创建变量
	 * @return
	 */
	protected Map<String, ? extends Serializable> createMessageVariables() {
		return null;
	}

}
