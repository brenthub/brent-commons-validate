package cn.brent.commons.validate.ogn;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.brent.commons.validate.ValidateException;

public class OGNParserRegistry {
	
	private final Logger logger = LoggerFactory.getLogger(OGNParserRegistry.class);
	
	private final Map<String, IOGNParser> cache = new HashMap<String, IOGNParser>();

    public OGNParserRegistry() {
        this.registerOGNParser("", new OGNDefaultParser());
    }

    public IOGNParser getOGNParser(final String id) {
    	IOGNParser ogn = cache.get(id);

        if (ogn == null){
            throw new ValidateException(id+" is not find.");
        }

        return ogn;
    }

    public void registerOGNParser(final String id, final IOGNParser ogn){
    	logger.info("OGNParser '{}' registered: {}", id, ogn);
        cache.put(id, ogn);
    }
}
