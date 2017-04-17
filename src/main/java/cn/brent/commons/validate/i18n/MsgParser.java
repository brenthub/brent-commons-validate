package cn.brent.commons.validate.i18n;

import java.util.Map;

public interface MsgParser {

	String renderMessage(final Object value, final String messageKey, final Map<String, ?> messageValues);
}
