package cn.brent.commons.validate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.brent.commons.validate.annotation.AnnotationsConfig;
import cn.brent.commons.validate.config.FieldConfig;
import cn.brent.commons.validate.config.IConfigurer;
import cn.brent.commons.validate.utils.ArrayUtils;
import cn.brent.commons.validate.utils.ReflectionUtils;

public class Validator implements IValidate {

	private final Logger logger = LoggerFactory.getLogger(Validator.class);

	private final Set<IConfigurer> configurers = new LinkedHashSet<IConfigurer>();

	private final Map<Class<?>, Checks> checksCache = new ConcurrentHashMap<Class<?>, Checks>();

	public Validator() {
		configurers.add(new AnnotationsConfig());
	}

	public Validator(final IConfigurer... configurers) {
		if (configurers != null) {
			for (final IConfigurer configurer : configurers) {
				this.configurers.add(configurer);
			}
		}
	}

	@Override
	public void assertValid(Object obj, String... profiles) {

		Class<?> clazz = obj.getClass();

		if (clazz == Object.class) {
			return;
		}

		if (checksCache.get(clazz) == null) {
			checksCache.put(clazz, new Checks(configurers));
		}

		Checks checks = checksCache.get(clazz);

		for (FieldConfig config : checks.getFieldConfigs()) {
			final Object value = resolveValue(config.getFiled(), obj);
			for (final Check check : config.getChecks()) {
				checkConstraint(check, obj, value, profiles, false);
			}
		}
	}

	protected void checkConstraint(final Check check, Object obj, Object value, final String[] profiles, final boolean isContainerValue) {
		
		if (!check.isActive(obj, value)){
			return;
		}

		final ConstraintTarget[] targets = check.getAppliesTo();

		if (!isContainerValue) {
			String target = check.getTarget();
			if (target != null) {//校验对象下的属性
				target = target.trim();
				if (target.length() > 0) {
					if (value == null){
						return;
					}
					final String[] chunks = target.split(":", 2);
					final String ognId, path;
					if (chunks.length == 1) {
						ognId = "";
						path = chunks[0];
					} else {
						ognId = chunks[0];
						path = chunks[1];
					}
					final ObjectGraphNavigationResult result = ognRegistry.getObjectGraphNavigator(ognId) //
							.navigateTo(value, path);
					if (result == null){
						return;
					}
					obj = result.targetParent;
					value = result.target;
					if (result.targetAccessor instanceof Field) {
						
					} else {
						
					}
				}
			}
		}

		final Class<?> compileTimeType = context.getCompileTimeType();

		final boolean isCollection = value != null ? //
		value instanceof Collection<?>
				: //
				Collection.class.isAssignableFrom(compileTimeType);
		final boolean isMap = !isCollection && //
				(value != null ? //
				value instanceof Map<?, ?>
						: //
						Map.class.isAssignableFrom(compileTimeType));
		final boolean isArray = !isCollection && !isMap && //
				(value != null ? //
				value.getClass().isArray()
						: //
						compileTimeType.isArray());
		final boolean isContainer = isCollection || isMap || isArray;

		if (isContainer && value != null)
			if (isCollection) {
				if (ArrayUtils.containsSame(targets, ConstraintTarget.VALUES)
						&& (!isContainerValue || ArrayUtils.containsSame(targets, ConstraintTarget.RECURSIVE))) {
					for (final Object item : (Collection<?>) value) {
						checkConstraint(violations, check, validatedObject, item, context, profiles, true);
					}
				}
			} else if (isMap) {
				if (ArrayUtils.containsSame(targets, ConstraintTarget.KEYS)
						&& (!isContainerValue || ArrayUtils.containsSame(targets, ConstraintTarget.RECURSIVE))) {
					for (final Object item : ((Map<?, ?>) value).keySet()) {
						checkConstraint(violations, check, validatedObject, item, context, profiles, true);
					}
				}

				if (ArrayUtils.containsSame(targets, ConstraintTarget.VALUES)
						&& (!isContainerValue || ArrayUtils.containsSame(targets, ConstraintTarget.RECURSIVE))) {
					for (final Object item : ((Map<?, ?>) value).values()) {
						checkConstraint(violations, check, validatedObject, item, context, profiles, true);
					}
				}
			} else
			// array
			{
				if (ArrayUtils.containsSame(targets, ConstraintTarget.VALUES)
						&& (!isContainerValue || ArrayUtils.containsSame(targets, ConstraintTarget.RECURSIVE))) {
					for (final Object item : ArrayUtils.asList(value)) {
						checkConstraint(violations, check, validatedObject, item, context, profiles, true);
					}
				}
			}

		if (isContainerValue || !isContainer || isContainer && ArrayUtils.containsSame(targets, ConstraintTarget.CONTAINER)) {
			_checkConstraint(violations, check, validatedObject, value, context, profiles);
		}
	}

	protected Object resolveValue(final Field field, final Object obj) {
		return ReflectionUtils.getFieldValue(field, obj);
	}

	@Override
	public List<ValConstraint> validate(Object obj, String... profiles) {
		// TODO Auto-generated method stub
		return null;
	}

}
