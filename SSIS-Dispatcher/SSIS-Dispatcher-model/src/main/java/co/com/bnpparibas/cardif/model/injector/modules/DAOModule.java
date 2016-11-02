package co.com.bnpparibas.cardif.model.injector.modules;


import co.com.bnpparibas.cardif.model.utils.NormalizedRead;
import co.com.bnpparibas.cardif.model.utils.NormalizedWrite;
import co.com.bnpparibas.cardif.model.utils.SimpleManager;
import co.com.bnpparibas.cardif.model.utils.PersistenceManager.ManagerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Scopes;

public class DAOModule extends AbstractModule{

	@Override
	protected void configure() {
		
		bind(Key.get(ManagerFactory.class, NormalizedWrite.class)).to(TestNormalizedManagerFactory.class);
		bind(TestNormalizedManagerFactory.class).in(Scopes.SINGLETON);

		bind(Key.get(ManagerFactory.class, NormalizedRead.class)).to(TestNormalizedManagerFactory.class);
		bind(TestNormalizedManagerFactory.class).in(Scopes.SINGLETON);
		
		bind(SimpleManager.class).in(Scopes.SINGLETON);
		
//		bind(LogManager.class).to(LogManagerModel.class).in(Scopes.SINGLETON);
	}

}
