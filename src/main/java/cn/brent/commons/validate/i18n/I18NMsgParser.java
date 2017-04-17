package cn.brent.commons.validate.i18n;

import java.util.Map;

public class I18NMsgParser implements MsgParser {

	@Override
	public String renderMessage(Object value, String messageKey, Map<String, ?> messageValues) {
		
		return messageKey;
	}

}
