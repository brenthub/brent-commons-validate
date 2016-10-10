package cn.brent.commons.validate;

/**
 * 当属性是一个Map或是一个容器时约束的作用范围
 */
public enum ConstraintTarget {
	
	/**
	 * 当为Map时，约束作用在keys上
	 */
	KEYS,

	/**
	 * 当为Map时，约束作用在Values上
	 */
	VALUES,

	/**
	 * 当集合内的元素还是集合时，进行递归
	 */
	RECURSIVE,

	/**
	 * 作用于容器本身
	 */
	CONTAINER
}
