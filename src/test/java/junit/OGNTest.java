package junit;

import org.junit.Test;

import cn.brent.commons.validate.ogn.OGNDefaultParser;
import cn.brent.commons.validate.ogn.OGNResult;

public class OGNTest {

	@Test
	public void testParse(){
		Mo m=new Mo();
		m.setId("moId");
		m.setName("Name");
		OGNResult o= new OGNDefaultParser().navigateTo(m, "name");
		System.out.println(o.getPath());
		System.out.println(o.getRoot());
		System.out.println(o.getTarget());
	}
}
