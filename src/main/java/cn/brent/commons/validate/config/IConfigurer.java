package cn.brent.commons.validate.config;


/**
 * 配置管理器
 */
public interface IConfigurer {

	ClassConfig getClassConfig(Class< ? > clazz);
	
}
