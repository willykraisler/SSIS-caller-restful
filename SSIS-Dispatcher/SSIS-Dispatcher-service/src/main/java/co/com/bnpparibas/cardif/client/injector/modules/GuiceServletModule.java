package co.com.bnpparibas.cardif.client.injector.modules;


import co.com.bnpparibas.cardif.model.facades.FacadeCallJob;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;

import org.glassfish.jersey.servlet.ServletContainer;

public class GuiceServletModule extends ServletModule {

	@Override
	protected void configureServlets() {

		bind(ServletContainer.class).in(Scopes.SINGLETON);
		bind(FacadeCallJob.class).in(Scopes.SINGLETON);
		serve("/service*").with(
				ServletContainer.class,
				ImmutableMap.of("javax.ws.rs.Application",
						"co.com.bnpparibas.cardif.client.injector.JerseyInjector"));



	}
}
