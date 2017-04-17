package cn.brent.commons.validate.ogn;

public class OGNResult {

	private final Object root;

	private final String path;

	private final Object targetParent;

	private final Object target;

	public OGNResult(final Object root, final String path, final Object targetParent, final Object target) {
		this.root = root;
		this.path = path;
		this.targetParent = targetParent;
		this.target = target;
	}

	public Object getRoot() {
		return root;
	}

	public String getPath() {
		return path;
	}

	public Object getTargetParent() {
		return targetParent;
	}

	public Object getTarget() {
		return target;
	}

}
