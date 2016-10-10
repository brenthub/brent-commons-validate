package cn.brent.validate.expression;

import java.util.HashMap;
import java.util.Map;

public class ExpressionLanguageFactory {

	public static Map<String,IExpressionLanguage> ELcache=new HashMap<String, IExpressionLanguage>();
	
	static{
		init();
	}
	
	public static void init(){
		register(new ExpressionLanguageJavaScriptImpl(),"js","javascript");
	}
	
	public static void register(IExpressionLanguage el,String... langs){
		for(String lang:langs){
			ELcache.put(lang, el);
		}
	}
	
	public static IExpressionLanguage get(String lang){
		IExpressionLanguage el=ELcache.get(lang);
		if(el==null){
			throw new RuntimeException("Not Find Expression language ["+lang+"] ");
		}
		return ELcache.get(lang);
	}
}
