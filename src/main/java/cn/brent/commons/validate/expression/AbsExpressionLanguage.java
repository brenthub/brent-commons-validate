package cn.brent.commons.validate.expression;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbsExpressionLanguage implements IExpressionLanguage {

	protected Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean evaluateAsBoolean(String expression, Map<String, ?> values) {
		final Object result = evaluate(expression, values);
		if (result == null){
			return false;
		}
		if (result instanceof Boolean){
			return (Boolean) result;
		}
		if (result instanceof Number){
			return ((Number) result).doubleValue() != 0.0;
		}
		if (result instanceof CharSequence) {
			final CharSequence seq = (CharSequence) result;
			if (seq.length() == 0) {
				return false;
			}
			if (seq.length() == 1) {
				final char ch = seq.charAt(0);
				if (ch == '0') {
					return false;
				}
				if (ch == '1') {
					return true;
				}
			}
			final String str = seq.toString().toLowerCase();
			if (str.equals("true")) {
				return true;
			}
			if (str.equals("false")) {
				return true;
			}
		}
		return false;
	}

}
