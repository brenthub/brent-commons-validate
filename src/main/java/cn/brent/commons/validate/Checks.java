package cn.brent.commons.validate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cn.brent.commons.validate.config.ClassConfig;
import cn.brent.commons.validate.config.FieldConfig;
import cn.brent.commons.validate.config.IConfigurer;

public class Checks {

	private Class<?> clazz;

	private Set<FieldConfig> fieldConfigs = new HashSet<FieldConfig>();

	public Checks(Class<?> clazz,final IConfigurer... configurers) {
		this.clazz=clazz;
		
		for(final IConfigurer configurer : configurers){
			final ClassConfig classConfig = configurer.getClassConfig(clazz);
			if (classConfig != null) {
				this.fieldConfigs=classConfig.getFieldConfigs();
			}
		}
	}

	public Checks(Class<?> clazz, final Collection<IConfigurer> configurers) {
		this(clazz, configurers.toArray(new IConfigurer[] {}));
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Set<FieldConfig> getFieldConfigs() {
		return fieldConfigs;
	}

}
