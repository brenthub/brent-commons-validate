package cn.brent.validate.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

public class ExpressionLanguageJavaScriptImpl extends AbsExpressionLanguage {

	private final Scriptable parentScope;

	private final Map<String, Script> scriptCache = new HashMap<String, Script>();

	public ExpressionLanguageJavaScriptImpl() {
		final Context ctx = ContextFactory.getGlobal().enterContext();
		try {
			parentScope = ctx.initStandardObjects();
		} finally {
			Context.exit();
		}
	}

	@Override
	public Object evaluate(String expression, Map<String, ?> values) {
		logger.debug("Evaluating JavaScript expression: {1}", expression);
		try {
			final Context ctx = ContextFactory.getGlobal().enterContext();
			Script script = scriptCache.get(expression);
			if (script == null) {
				ctx.setOptimizationLevel(9);
				script = ctx.compileString(expression, "<cmd>", 1, null);
				scriptCache.put(expression, script);
			}
			final Scriptable scope = ctx.newObject(parentScope);
			scope.setPrototype(parentScope);
			scope.setParentScope(null);
			for (final Entry<String, ?> entry : values.entrySet()) {
				scope.put(entry.getKey(), scope, Context.javaToJS(entry.getValue(), scope));
			}
			return script.exec(ctx, scope);
		} catch (final EvaluatorException ex) {
			throw new RuntimeException("Evaluating JavaScript expression failed: " + expression, ex);
		} finally {
			Context.exit();
		}
	}

}
