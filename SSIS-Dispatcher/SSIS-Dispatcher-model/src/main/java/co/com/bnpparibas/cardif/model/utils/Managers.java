package co.com.bnpparibas.cardif.model.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import co.com.bnpparibas.cardif.model.utils.PersistenceManager.ManagerFactory;

import com.google.inject.Inject;


public class Managers {
	
	private static String NORMALIZED_READ = "normalized";	
	private static String NORMALIZED_WRITE = "normalized";

	public static class NormalizedRead implements ManagerFactory{
		
		private EntityManagerFactory emf;
		
		@Inject
		public NormalizedRead(SystemWrapper system) {
			emf = Persistence
					.createEntityManagerFactory(NORMALIZED_READ);
			
		}
		
		@Override
		public EntityManagerFactory getEntityManagerFactory() {
			return emf;
		}
		
	}
	
	public static class NormalizedWrite implements ManagerFactory{

		private EntityManagerFactory emf;
		
		@Inject
		public NormalizedWrite(SystemWrapper system) {
			emf = Persistence
					.createEntityManagerFactory(NORMALIZED_WRITE);
		}
		
		@Override
		public EntityManagerFactory getEntityManagerFactory() {
			return emf;
		}
		
	}
	
}
