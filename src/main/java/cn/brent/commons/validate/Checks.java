package cn.brent.commons.validate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cn.brent.commons.validate.config.FieldConfig;
import cn.brent.commons.validate.config.IConfigurer;

public class Checks {

	private Class <?> clazz;
	
	private Set<FieldConfig> fieldConfigs=new HashSet<FieldConfig>();
	
	public Checks(final IConfigurer... configurers) {
		
	}
	
	public Checks(final Collection<IConfigurer> configurers) {
		this(configurers.toArray(new IConfigurer[]{}));
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Set<FieldConfig> getFieldConfigs() {
		return fieldConfigs;
	}
	
}
