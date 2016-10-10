package cn.brent.commons.validate.expression;

import java.util.Map;

/**
 * 表达式语言
 */
public interface IExpressionLanguage {
	
	/**
	 * 求表达式值
	 * @param expression
	 * @param values
	 * @return
	 */
	Object evaluate(String expression, Map<String, ? > values);

	/**
	 * 求表达式布尔值
	 * @param expression
	 * @param values
	 * @return
	 */
	boolean evaluateAsBoolean(String expression, Map<String, ? > values);
	
}
