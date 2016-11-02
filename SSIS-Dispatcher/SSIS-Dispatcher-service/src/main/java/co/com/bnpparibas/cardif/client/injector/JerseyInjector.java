package co.com.bnpparibas.cardif.client.injector;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import co.com.bnpparibas.cardif.client.injector.GuiceServletInjector.InjectorProvider;

public class JerseyInjector extends ResourceConfig{

	@Inject
	public JerseyInjector(ServiceLocator locator) {
		packages("co.com.bnpparibas.cardif.services");
		
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(locator);

        GuiceIntoHK2Bridge guiceBridge = locator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(InjectorProvider.getInjector());
	}
	
}
