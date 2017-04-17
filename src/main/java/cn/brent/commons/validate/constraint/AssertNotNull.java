package cn.brent.commons.validate.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.brent.commons.validate.ConstraintTarget;
import cn.brent.commons.validate.annotation.Constraint;
import cn.brent.commons.validate.annotation.Constraints;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Constraint(checkWith = AssertNotNullCheck.class)
public @interface AssertNotNull {

	@Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
    @Constraints
    public @interface List {
		
        AssertNotNull[] value();

        String when() default "";
    }

    ConstraintTarget[] appliesTo() default { ConstraintTarget.CONTAINER, ConstraintTarget.VALUES };

    String errorCode() default "validate.AssertNotNull";

    String message() default "validate.AssertNotNull.msg";

    String[] profiles() default {};

    int severity() default 0;

    String target() default "";

    String when() default "";
}
