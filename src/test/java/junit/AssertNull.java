package junit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.brent.validate.ConstraintTarget;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface  AssertNull{
	
	ConstraintTarget[] appliesTo() default {ConstraintTarget.CONTAINER, ConstraintTarget.VALUES};
	
	String errorCode() default "net.sf.oval.constraint.AssertNull";

	String message() default "net.sf.oval.constraint.AssertNull.violated";

	String[] profiles() default {};
	
	int severity() default 0;
	
	String target() default "";

	String when() default "";
}
