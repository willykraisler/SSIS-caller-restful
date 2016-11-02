package co.com.bnpparibas.cardif.model.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import co.com.bnpparibas.cardif.model.exception.PersistenceException;

public interface PersistenceManager extends Serializable{
	
	public static interface TransactionManager{
	
		public void doTransaction(EntityManager em) throws PersistenceException;
		
	}
	
	public static interface ManagerFactory{
		
		public EntityManagerFactory getEntityManagerFactory();
		
	}
	
	public void insertar(@Nonnull Object entity) throws PersistenceException;
	
	public void actualizar(@Nonnull Object entity) throws PersistenceException;
	
	public void eliminar(@Nonnull Object entity, Class<?> clazz) throws PersistenceException;
	
	
	public <T> T buscar(@Nonnull Object key, @Nonnull Class<T> clazz) throws PersistenceException;
	
	public <T> T consultaUnica(@Nonnull String namedQuery, @Nonnull Map<String, Object> params, @Nonnull Class<T> clazz) throws PersistenceException;
	
	public <T> List<T> consultar(@Nonnull String namedQuery,@Nonnull Class<T> clazz) throws PersistenceException;
	
	public <T> List<T> consultar(@Nonnull String namedQuery,@Nonnull Map<String, Object> params, @Nonnull Class<T> clazz) throws PersistenceException;
	
	public <T> List<T> consultar(@Nonnull String namedQuery,@Nonnull Integer limite, @Nonnull Map<String, Object> params, @Nonnull Class<T> clazz) throws PersistenceException;
	
	public void callTransaction(@Nonnull TransactionManager transactionManger) throws PersistenceException;
	
	
	
}
