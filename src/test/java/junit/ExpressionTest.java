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
		boolean b =el.evaluateAsBoolean("_this.name=='name'", p);
		System.out.println(b);
	}

}
