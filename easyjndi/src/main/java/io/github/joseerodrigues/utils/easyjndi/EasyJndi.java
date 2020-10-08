package io.github.joseerodrigues.utils.easyjndi;

import javax.naming.Context;
import javax.naming.InitialContext;

public class EasyJndi {

	public static Context install() {

		String easyFactoryName = EasyJndiContextFactory.CLASSNAME;

		String previousFactory = System.getProperty(InitialContext.INITIAL_CONTEXT_FACTORY, "");

		if (!previousFactory.isEmpty() && !easyFactoryName.equals(previousFactory)) {
			EasyJndiContextFactory.oldFactory = previousFactory;
		}

		System.setProperty(InitialContext.INITIAL_CONTEXT_FACTORY, easyFactoryName);

		EasyJndiInitialContext ctx = EasyJndiContextFactory.makeNewCtx();

		return ctx;
	}

}
