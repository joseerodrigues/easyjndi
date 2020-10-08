package io.github.joseerodrigues.utils.easyjndi;

import static org.junit.Assert.assertEquals;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Test;

public class EasyJndiTest {

	@Test
	public void testMapStr() throws NamingException {
		String value = "val";
		
		Context ctx = EasyJndi.install();
						
		ctx.bind("test", value);
		
		assertEquals(ctx.lookup("test"), value);
		
		//
		InitialContext newCtx = new InitialContext();
		
		assertEquals(newCtx.lookup("test"), value);
	}
}
