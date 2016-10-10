package cn.brent.commons.validate.config;

import java.util.HashSet;
import java.util.Set;

public class ClassConfig {

	private Class<?> type;

	private Set<FieldConfig> fieldConfigs=new HashSet<FieldConfig>();

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Set<FieldConfig> getFieldConfigs() {
		return fieldConfigs;
	}

	public void addFieldConfig(FieldConfig fieldConfig) {
		this.fieldConfigs.add(fieldConfig);
	}

}
