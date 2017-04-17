package junit;

import cn.brent.commons.validate.constraint.AssertNotNull;

public class Mo {

	@AssertNotNull(profiles="A")
	private String id;
	
	@AssertNotNull(when="_this.id != null")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
