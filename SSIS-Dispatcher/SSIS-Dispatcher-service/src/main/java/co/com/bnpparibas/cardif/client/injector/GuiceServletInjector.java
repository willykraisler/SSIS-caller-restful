package co.com.bnpparibas.cardif.client.injector;

import javax.servlet.ServletContextEvent;

import co.com.bnpparibas.cardif.client.injector.modules.GuiceServletModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;


public class GuiceServletInjector extends GuiceServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
	}
	
	@Override
	protected Injector getInjector() {
		return InjectorProvider.getInjector();
	}

	/*
	 * Implementación de un Lazy Singleton, el objeto es instanciado cuando se referencia la clase. http://en.wikipedia.org/wiki/Singleton_pattern#The_solution_of_Bill_Pugh  
	 */
	public static class InjectorProvider {
		private static Injector injector = Guice.createInjector(
				new GuiceServletModule());

		public static Injector getInjector() {
			return injector;
		}
	}
	
}
