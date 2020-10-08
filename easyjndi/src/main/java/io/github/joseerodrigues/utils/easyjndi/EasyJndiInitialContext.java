package io.github.joseerodrigues.utils.easyjndi;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EasyJndiInitialContext extends InitialContext implements Context{

	private Map<String, Object> lookupMap = Collections.emptyMap();
	private String fallbackFactory = null;
			
	public EasyJndiInitialContext() throws NamingException {
		super(true); 
		lookupMap = new ConcurrentHashMap<>();
	}

	public Map<String, Object> getLookupMap() {
		return lookupMap;
	}

	public void setLookupMap(Map<String, Object> lookupMap, String fallbackFactory) {
		this.lookupMap = lookupMap;
		this.fallbackFactory = fallbackFactory;
	}

	public EasyJndiInitialContext(boolean lazy) throws NamingException {
		super(true);
		lookupMap = new ConcurrentHashMap<>();
	}


	public EasyJndiInitialContext(Hashtable<?, ?> environment) throws NamingException {
		super(true);
		lookupMap = new ConcurrentHashMap<>();
	}

	public EasyJndiInitialContext(Hashtable<?, ?> environment, Map<String, Object> lookupMap, String fallbackFactory) throws NamingException {
		super(true);
		this.lookupMap = lookupMap;
		this.fallbackFactory = fallbackFactory;
	}

	@Override
	public Object lookup(String name) throws NamingException {
		Object ret = this.lookupMap.get(name);
		
		if (ret == null && this.fallbackFactory != null) {
			
			Hashtable<Object, Object> environment = new Hashtable<>();
			environment.put(InitialContext.INITIAL_CONTEXT_FACTORY, this.fallbackFactory);
			
			InitialContext ctx = new InitialContext(environment);
			
			ret = ctx.lookup(name);
		}
		
		return ret;
	}

	@Override
	public void bind(String name, Object obj) throws NamingException {
		this.lookupMap.put(name, obj);
	}
}
