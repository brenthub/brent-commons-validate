package cn.brent.commons.validate.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.brent.commons.validate.Check;
import cn.brent.commons.validate.ValidateException;
import cn.brent.commons.validate.config.ClassConfig;
import cn.brent.commons.validate.config.FieldConfig;
import cn.brent.commons.validate.config.IConfigurer;

public class AnnotationsConfig implements IConfigurer {

	@Override
	public ClassConfig getClassConfig(Class<?> clazz) {
		final ClassConfig classCfg = new ClassConfig();
		classCfg.setType(clazz);
		configureFieldChecks(classCfg);
		return classCfg;
	}

	private void configureFieldChecks(ClassConfig classCfg) {

		for (final Field field : classCfg.getType().getDeclaredFields()) {

			List<Check> checks = new ArrayList<Check>();
			// loop over all annotations of the current field
			for (final Annotation annotation : field.getAnnotations()) {
				// check if the current annotation is a constraint annotation
				if (annotation.annotationType().isAnnotationPresent(Constraint.class)) {
					Check ck= initializeCheck(annotation);
					ck.setField(field);
					checks.add(ck);
				}
			}

			if (checks.size() > 0) {
				final FieldConfig fc = new FieldConfig();
				fc.setName(field.getName());
				fc.setFiled(field);
				fc.setChecks(checks);
				classCfg.addFieldConfig(fc);
			}

		}

	}

	@SuppressWarnings("unchecked")
	protected <T extends Annotation> Check initializeCheck(T annotation) {

		final Constraint constraint = annotation.annotationType().getAnnotation(Constraint.class);

		final Class<IAnnotationCheck<T>> checkClass = (Class<IAnnotationCheck<T>>) constraint.checkWith();

		// instantiate the appropriate check for the found constraint
		final IAnnotationCheck<T> check = newCheckInstance(checkClass);

		check.configure(annotation);

		return check;
	}

	/**
	 * 反射创建检查类实例
	 * @param checkClass
	 * @return
	 */
	protected <T extends Annotation> IAnnotationCheck<T> newCheckInstance(final Class<IAnnotationCheck<T>> checkClass){
		try {
			return checkClass.newInstance();
		} catch (final InstantiationException ex) {
			throw new ValidateException("Cannot initialize constraint check " + checkClass.getName(), ex);
		} catch (final IllegalAccessException ex) {
			throw new ValidateException("Cannot initialize constraint check " + checkClass.getName(), ex);
		}
	}
}
