package cn.brent.commons.validate.ogn;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.brent.commons.validate.ValidateException;
import cn.brent.commons.validate.utils.ReflectionUtils;

public class OGNDefaultParser implements IOGNParser {

	@Override
	public OGNResult navigateTo(Object root, String path) {
		Object parent = null;
		Object target = root;
		for (final String chunk : path.split("\\.")) {
			parent = target;
			if (parent == null) {
				return null;
			}
			final Field field = ReflectionUtils.getFieldRecursive(parent.getClass(), chunk);
			if (field == null) {
				final Method getter = ReflectionUtils.getGetterRecursive(parent.getClass(), chunk);
				if (getter == null) {
					throw new ValidateException(
							"Invalid path from root object class [" + root.getClass().getName() + "] path: " + path);
				}
				target = ReflectionUtils.invokeMethod(getter, parent);
			} else {
				target = ReflectionUtils.getFieldValue(field, parent);
			}
		}
		return new OGNResult(root, path, parent, target);
	}

}
