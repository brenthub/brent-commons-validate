package cn.brent.commons.validate.ogn;


public class OGNResult {

	public final Object root;

	public final String path;

	public final Object targetParent;

	public final Object target;

	public OGNResult(final Object root, final String path, final Object targetParent, final Object target) {
		this.root = root;
		this.path = path;
		this.targetParent = targetParent;
		this.target = target;
	}
}
