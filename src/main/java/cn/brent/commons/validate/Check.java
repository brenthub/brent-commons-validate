package cn.brent.commons.validate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 
 */
public interface Check {
	

	Field getField();
	
	void setField(Field field);
	
	/**
	 * 当为集合时的作用范围
	 * @return
	 */
	ConstraintTarget[] getAppliesTo();
	
	void setAppliesTo(ConstraintTarget... target);

	/**
	 * 异常码
	 * @return
	 */
	String getErrorCode();
	
	void setErrorCode(String errorCode);
	
	/**
	 * 异常消息
	 * @return
	 */
	String getMessage();
	
	void setMessage(String message);

	/**
	 * 条件
	 * @return
	 */
	String[] getProfiles();
	
	void setProfiles(String... profiles);
	
	/**
	 * 严重程度，数字超大表示越严重
	 * @return
	 */
	int getSeverity();
	
	void setSeverity(int severity);

	/**
	 * 用于较验对象下面某一个属性
	 * @return
	 */
	String getTarget();
	
	void setTarget(String target);

	/**
	 * 表达式
	 * @return
	 */
	String getWhen();
	
	void setWhen(String when);

	/**
	 * 是否是激活的（当when存在时，需要执行表达式才能判断是否执行）
	 * @param validatedObject 被校验对象
	 * @param valueToValidate 字段值
	 * @return
	 */
	boolean isActive(Object validatedObject, Object valueToValidate);

	/**
	 * 是否校验通过
	 * @param validatedObject 被校验对象
	 * @param valueToValidate 字段值
	 * @param context
	 * @return
	 */
	boolean isSatisfied(Object validatedObject, Object valueToValidate);
	
	/**
	 * 获取异常消息变量（消息模板有占位符）
	 * @return
	 */
	Map<String, ? extends Serializable> getMessageVariables();
	
}
