package junit;

import org.junit.Test;

import cn.brent.commons.validate.Validator;

public class ValidateTest {

	@Test
	public void testValidate(){
		Mo m=new Mo();
		
		Validator v = new Validator();
		
		System.out.println(v.validate(m));
		
		m.setId("dd");
		System.out.println(v.validate(m,"A"));
		
		v.assertValid(m);
	}
}
