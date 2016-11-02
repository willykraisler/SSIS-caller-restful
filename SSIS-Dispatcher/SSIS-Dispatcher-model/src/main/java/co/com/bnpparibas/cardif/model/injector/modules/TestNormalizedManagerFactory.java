package co.com.bnpparibas.cardif.model.injector.modules;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import co.com.bnpparibas.cardif.model.utils.PersistenceManager.ManagerFactory;


public class TestNormalizedManagerFactory implements ManagerFactory{

	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("normalized");
	
	
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	/*
	 * Si no se cierra, causa deadlock 
	 */
	public void reset() {
		emf.close();
		emf = Persistence
				.createEntityManagerFactory("normalized");
	}
	
}