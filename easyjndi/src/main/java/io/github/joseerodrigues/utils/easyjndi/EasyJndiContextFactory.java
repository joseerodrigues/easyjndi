package io.github.joseerodrigues.utils.easyjndi;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class EasyJndiContextFactory implements InitialContextFactory{

	static final String CLASSNAME = EasyJndiContextFactory.class.getName();
	private static final Map<String, Object> lookupMap = new ConcurrentHashMap<>();
	static String oldFactory = null;
	
	@Override
	public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {

		String factory = (String)environment.get(InitialContext.INITIAL_CONTEXT_FACTORY);
				
		if (CLASSNAME.equals(factory)) {
			return new EasyJndiInitialContext(environment, lookupMap, oldFactory);
		}
		
		Hashtable<Object, Object>env = new Hashtable<>(environment);
		if (oldFactory != null) {				
			env.put(InitialContext.INITIAL_CONTEXT_FACTORY, oldFactory);
		}
		
		return new InitialContext(env);
	}

	static EasyJndiInitialContext makeNewCtx() {
		
		EasyJndiInitialContext ctx;
		try {
			ctx = new EasyJndiInitialContext();
			ctx.setLookupMap(lookupMap, oldFactory);
			
			return ctx;
		} catch (NamingException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}		
	}	
}
