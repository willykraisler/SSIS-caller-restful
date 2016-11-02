package co.com.bnpparibas.cardif.persistencia;

import java.io.Serializable;

import javax.annotation.Nonnull;

import co.com.bnpparibas.cardif.model.exception.PersistenceException;

public interface GenericDAO<T> extends Serializable{

	public void insertar(@Nonnull T entity) throws PersistenceException;
	
	public void actualizar(@Nonnull T entity) throws PersistenceException;
	
	public void eliminar(@Nonnull T entity) throws PersistenceException;
	
	public T buscar(@Nonnull Object object) throws PersistenceException;
	
}
