package cn.brent.commons.validate.annotation;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.brent.commons.validate.AbsCheck;
import cn.brent.commons.validate.ConstraintTarget;

/**
 * 注解抽象基类
 *
 * @param <T>
 */
public abstract class AbsAnnotationCheck<T extends Annotation> extends AbsCheck implements IAnnotationCheck<T> {

	protected static final Logger logger = LoggerFactory.getLogger(AbsAnnotationCheck.class);

	protected void configure(ConstraintTarget[] appliesTo,String message,String errorCode,int severity,String[] profiles,String target,String when ){
		setAppliesTo(appliesTo);
		setMessage(message);
		setErrorCode(errorCode);
		setSeverity(severity);
		setProfiles(profiles);
		setTarget(target);
		setWhen(when);
	}

}
