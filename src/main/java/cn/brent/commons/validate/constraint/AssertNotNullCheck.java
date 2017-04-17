package cn.brent.commons.validate.constraint;

import org.apache.commons.lang3.StringUtils;

import cn.brent.commons.validate.annotation.AbsAnnotationCheck;

public class AssertNotNullCheck extends AbsAnnotationCheck<AssertNotNull> {

	public boolean isSatisfied(final Object object, final Object value) {
		if(value==null){
			return false;
		}
		if(value instanceof String){
			return StringUtils.isNotEmpty(value.toString());
		}
		return value != null;
	}

	@Override
	public void configure(AssertNotNull annotation) {
		configure(annotation.appliesTo(), 
				annotation.message(), 
				annotation.errorCode(), 
				annotation.severity(), 
				annotation.profiles(), 
				annotation.target(),
				annotation.when());
	}

}
