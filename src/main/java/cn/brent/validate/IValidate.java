package cn.brent.validate;

import java.util.List;

/**
 * 自定义校验接口
 */
public interface IValidate {

	/**
	 * 断言可用
	 * 
	 * @param obj
	 * @param profiles
	 */
	void assertValid(final Object obj, String... profiles);

	/**
	 * 获取所有未通过的约束
	 * 
	 * @param obj
	 * @param profiles
	 * @return
	 */
	List<ValConstraint> validate(final Object obj, String... profiles);
}
