package cn.brent.commons.validate;

import java.util.ArrayList;
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
import cn.brent.commons.validate.i18n.I18NMsgParser;
import cn.brent.commons.validate.i18n.MsgParser;
import cn.brent.commons.validate.ogn.OGNParserRegistry;
import cn.brent.commons.validate.ogn.OGNResult;
import cn.brent.commons.validate.utils.ArrayUtils;
import cn.brent.commons.validate.utils.ReflectionUtils;

public class Validator implements IValidate {

	protected final Logger logger = LoggerFactory.getLogger(Validator.class);

	private final Set<IConfigurer> configurers = new LinkedHashSet<IConfigurer>();

	private final Map<Class<?>, Checks> checksCache = new ConcurrentHashMap<Class<?>, Checks>();

	private final OGNParserRegistry ognRegistry = new OGNParserRegistry();
	
	private final MsgParser msgParser=new I18NMsgParser();

	public Validator() {
		this(new AnnotationsConfig());
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
			checksCache.put(clazz, new Checks(clazz, configurers));
		}

		Checks checks = checksCache.get(clazz);

		for (FieldConfig config : checks.getFieldConfigs()) {
			final Object value = ReflectionUtils.getFieldValue(config.getFiled(), obj);
			for (final Check check : config.getChecks()) {
				ValConstraint cons = checkConstraint(check, obj, value, profiles, false);
				if(cons!=null){
					throw new ValidateException(cons.getErrorCode(),cons.getMessage());
				}
			}
		}
	}

	protected ValConstraint checkConstraint(final Check check, Object obj, Object value, final String[] profiles, final boolean isContainerValue) {

		if (!check.isActive(obj, value)) {
			return null;
		}

		final ConstraintTarget[] targets = check.getAppliesTo();

		if (!isContainerValue) {
			String target = check.getTarget();
			if (target != null) {// 校验对象下的属性
				target = target.trim();
				if (target.length() > 0) {
					if (value == null) {
						return null;
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
					final OGNResult result = ognRegistry.getOGNParser(ognId).navigateTo(value, path);
					if (result == null) {
						return null;
					}
					obj = result.getTargetParent();
					value = result.getTarget();
				}
			}
		}

		final Class<?> clzType = obj.getClass();

		final boolean isCollection = value != null ? value instanceof Collection<?> : Collection.class.isAssignableFrom(clzType);
		final boolean isMap = !isCollection && (value != null ? value instanceof Map<?, ?> : Map.class.isAssignableFrom(clzType));
		final boolean isArray = !isCollection && !isMap && (value != null ? value.getClass().isArray() : clzType.isArray());
		final boolean isContainer = isCollection || isMap || isArray;

		if (isContainer && value != null) {
			if (isCollection) {
				if (ArrayUtils.containsSame(targets, ConstraintTarget.VALUES)
						&& (!isContainerValue || ArrayUtils.containsSame(targets, ConstraintTarget.RECURSIVE))) {
					for (final Object item : (Collection<?>) value) {
						checkConstraint(check, obj, item, profiles, true);
					}
				}
			} else if (isMap) {
				if (ArrayUtils.containsSame(targets, ConstraintTarget.KEYS)
						&& (!isContainerValue || ArrayUtils.containsSame(targets, ConstraintTarget.RECURSIVE))) {
					for (final Object item : ((Map<?, ?>) value).keySet()) {
						checkConstraint(check, obj, item, profiles, true);
					}
				}

				if (ArrayUtils.containsSame(targets, ConstraintTarget.VALUES)
						&& (!isContainerValue || ArrayUtils.containsSame(targets, ConstraintTarget.RECURSIVE))) {
					for (final Object item : ((Map<?, ?>) value).values()) {
						checkConstraint(check, obj, item, profiles, true);
					}
				}
			} else {
				if (ArrayUtils.containsSame(targets, ConstraintTarget.VALUES)
						&& (!isContainerValue || ArrayUtils.containsSame(targets, ConstraintTarget.RECURSIVE))) {
					for (final Object item : ArrayUtils.asList(value)) {
						checkConstraint(check, obj, item, profiles, true);
					}
				}
			}
		}

		if (isContainerValue || !isContainer || isContainer && ArrayUtils.containsSame(targets, ConstraintTarget.CONTAINER)) {
			if (!check.isSatisfied(obj, value)) {
				final String errorMessage = msgParser.renderMessage( value, check.getMessage(), check.getMessageVariables());
				return new ValConstraint(check, errorMessage, obj, value);
			}
		}
		return null;
	}


	@Override
	public List<ValConstraint> validate(Object obj, String... profiles) {
		Class<?> clazz = obj.getClass();

		List<ValConstraint> result=new ArrayList<ValConstraint>(); 
		
		if (clazz == Object.class) {
			return result;
		}

		if (checksCache.get(clazz) == null) {
			checksCache.put(clazz, new Checks(clazz, configurers));
		}

		Checks checks = checksCache.get(clazz);

		for (FieldConfig config : checks.getFieldConfigs()) {
			final Object value = ReflectionUtils.getFieldValue(config.getFiled(), obj);
			for (final Check check : config.getChecks()) {
				ValConstraint cons = checkConstraint(check, obj, value, profiles, false);
				if(cons!=null){
					result.add(cons);
				}
			}
		}
		
		return result;
	}

}
