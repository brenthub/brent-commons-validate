package cn.brent.validate.annotation;

import java.lang.annotation.Annotation;

import cn.brent.validate.AbsCheck;
import cn.brent.validate.ConstraintTarget;

/**
 * 注解抽象基类
 *
 * @param <T>
 */
public abstract class AbsAnnotationCheck<T extends Annotation> extends AbsCheck implements IAnnotationCheck<T> {
	
	
	public void configure(ConstraintTarget[] appliesTo,String message,String errorCode,int severity,String[] profiles,String target,String when ){
		setAppliesTo(appliesTo);
		setMessage(message);
		setErrorCode(errorCode);
		setSeverity(severity);
		setProfiles(profiles);
		setTarget(target);
		setWhen(when);
	}
	
}
