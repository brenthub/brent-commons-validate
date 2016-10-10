package junit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.brent.commons.validate.expression.ExpressionLanguageFactory;
import cn.brent.commons.validate.expression.IExpressionLanguage;

public class ExpressionTest {

	@Test
	public void testJS() {

		IExpressionLanguage el = ExpressionLanguageFactory.get("js");
		Map<String, Object> p = new HashMap<String, Object>();
		Mo m=new Mo();
		m.setId("33");
		m.setName("name");
		p.put("_this", m);
		//Object n = el.evaluate("_this.name", p);
		boolean b =el.evaluateAsBoolean("_this.name=='1ame'", p);
		System.out.println(b);
	}

	public static class Mo {
		private String id;
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
}
