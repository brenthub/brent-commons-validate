package cn.brent.commons.validate.annotation;

import java.lang.annotation.Annotation;

import cn.brent.commons.validate.Check;

/**
 * 基于注解的配置
 * @param <T>
 */
public interface IAnnotationCheck <T extends Annotation> extends Check{

	/**
	 * @param annotation
	 */
	public void configure(T annotation);
	
}
