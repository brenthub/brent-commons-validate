package cn.brent.validate.config;

import java.lang.reflect.Field;
import java.util.List;

import cn.brent.validate.Check;

public class FieldConfig {

	private String name;

	private Field filed;

	private List<Check> checks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Check> getChecks() {
		return checks;
	}

	public void setChecks(List<Check> checks) {
		this.checks = checks;
	}

	public Field getFiled() {
		return filed;
	}

	public void setFiled(Field filed) {
		this.filed = filed;
	}

}
