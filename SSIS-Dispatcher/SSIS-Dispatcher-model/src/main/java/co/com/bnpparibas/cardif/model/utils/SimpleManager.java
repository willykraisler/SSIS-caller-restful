package co.com.bnpparibas.cardif.model.utils;

import static com.google.common.base.Preconditions.checkArgument;
//import static com.utils.types.CastUtils.secureCast;


import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import org.hibernate.HibernateException;
import co.com.bnpparibas.cardif.model.BaseEntity;
//import com.shared.modelo.log.LogInfo;
//import com.shared.modelo.log.LogManagerModel;
//import com.shared.modelo.log.LogInfo.DML;
import co.com.bnpparibas.cardif.model.exception.BadResultException;
import co.com.bnpparibas.cardif.model.exception.DeleteException;
import co.com.bnpparibas.cardif.model.exception.InsertException;
import co.com.bnpparibas.cardif.model.exception.NotFoundException;
import co.com.bnpparibas.cardif.model.exception.PersistenceException;
import co.com.bnpparibas.cardif.model.exception.SearchException;
import co.com.bnpparibas.cardif.model.exception.UpdateException;
//import com.util.log.LogManager;
//import com.utils.types.TypeNamedGeneric;

/*
 * FIXME JDBCConnectionException
 * 
 * Cuando las conexión con base  de datos falla,
 * lanza org.hibernate.exception.JDBCConnectionException
 * sigue tratando de usar la conexión rota.
 * 
 */

/*
 * Siempre que se inicia una transacción hay que 
 * terminarla, de lo contrario hay leak de 
 * conexiones.
 */
public class SimpleManager implements PersistenceManager {

	private static final long serialVersionUID = 1L;

	protected ManagerFactory read;

	protected ManagerFactory write;
	
//	protected LogManager logManager;
	
	
	@Inject	
	public SimpleManager(@NormalizedRead ManagerFactory read, @NormalizedWrite ManagerFactory write/*, LogManagerModel logManager*/) {
		this.read = read;
		this.write = write;
//		this.logManager = logManager;
	}
	
	private void setFechaCreacion (Object object){
		BaseEntity entity = secureCast(object, BaseEntity.class);
		
		if(entity == null)
			return;
		
		Date now = new Date();
		entity.setFechaCreacion(now);
		entity.setFechaActualizacion(now);
	}
	
	private void setFechaActualizacion (Object object){
		BaseEntity entity = secureCast(object, BaseEntity.class);
		
		if(entity == null)
			return;
		
		entity.setFechaActualizacion(new Date());
	}	

	@Override
	public void insertar(@Nonnull Object entity) throws InsertException {
				
		Date init = new Date();
		
		EntityManager em = null;
		setFechaCreacion(entity);
		
		try {
			checkArgument(entity != null);
			Object completeEntity = completeEntity(entity);

			em = write.getEntityManagerFactory().createEntityManager();

			em.getTransaction().begin();

			em.persist(completeEntity);

			em.getTransaction().commit();

		} catch (IllegalStateException exception) {
			
			////logManager.logError(exception);

			throw new InsertException();

		} catch (EntityExistsException exception) {

			////logManager.logError(exception);
			
			throw new InsertException();

		} catch (IllegalArgumentException exception) {
			
			////logManager.logError(exception);
			
			throw new InsertException();

		} catch (javax.persistence.PersistenceException exception) {

			if (em != null && em.getTransaction().isActive())
				em.getTransaction().rollback();

			////logManager.logError(exception);
			
			throw new InsertException();

		} catch (HibernateException exception) {

			if (em != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			
			////logManager.logError(exception);
			
			throw new InsertException();

		} finally {
		
			if (em != null)
				em.close();
			
			Date finish = new Date();
			long result = finish.getTime() - init.getTime() ;
			//logManager.logInfo(new LogInfo(DML.INSERT,TypeNamedGeneric.takeNameClass(entity.getClass().getName()),"insertar", result, "insertar()").toString());

		}
	}

	@Override
	public void actualizar(@Nonnull Object entity) throws UpdateException {

		Date init = new Date();
		
		EntityManager em = null;
		setFechaActualizacion(entity);
		
		try {

			checkArgument(entity != null);

			em = write.getEntityManagerFactory().createEntityManager();

			em.getTransaction().begin();

			em.merge(entity);

			em.getTransaction().commit();

		} catch (IllegalStateException exception) {
			
			////logManager.logError(exception);
			
			throw new UpdateException();

		} catch (IllegalArgumentException exception) {
			
			////logManager.logError(exception);
			
			throw new UpdateException();

		} catch (javax.persistence.PersistenceException exception) {
			
			////logManager.logError(exception);
			
			if (em != null && em.getTransaction().isActive())
				em.getTransaction().rollback();

			throw new UpdateException();

		} catch (HibernateException exception) {
			
			if (em != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			
			////logManager.logError(exception);
	     	
			throw new UpdateException();

		} finally{

			if (em != null)
				em.close();
			
			Date finish = new Date();
			long result = finish.getTime() - init.getTime() ;
			//logManager.logInfo(new LogInfo(DML.UPDATE,TypeNamedGeneric.takeNameClass(entity.getClass().getName()),"actulizar", result, "actualizar()").toString());

		}

	}

	@Override
	public void eliminar(@Nonnull Object id, @Nonnull Class<?> clazz) throws DeleteException {
		
		Date init = new Date();
		EntityManager em = null;

		try {

			checkArgument(id != null);
			checkArgument(clazz != null);

			em = write.getEntityManagerFactory().createEntityManager();

			checkArgument(id != null);

			em.getTransaction().begin();

			Object attached = em.find(clazz, id);

			em.remove(attached);

			em.getTransaction().commit();

		} catch (IllegalStateException exception) {

			////logManager.logError(exception);

			throw new DeleteException();

		} catch (IllegalArgumentException exception) {
			
			////logManager.logError(exception);
			
			throw new DeleteException();

		} catch (javax.persistence.PersistenceException exception) {
			
			if (em != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			
			////logManager.logError(exception);

			throw new DeleteException();

		} catch (HibernateException exception) {
			
			if (em != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			
			////logManager.logError(exception);

			throw new DeleteException();

		} finally {

			if (em != null)
				em.close();
			
			Date finish = new Date();
			long result = finish.getTime() - init.getTime() ;
			//logManager.logInfo(new LogInfo(DML.DELETE,TypeNamedGeneric.takeNameClass(clazz.getClass().getName()),"actulizar", result, "eliminar()").toString());


		}

	}

	@Override
	public <T> T buscar(@Nonnull Object key, @Nonnull Class<T> clazz) throws SearchException, NotFoundException {
		Date init = new Date();
		EntityManager em = null;

		try {

			checkArgument(key != null);
			checkArgument(clazz != null);

			em = read.getEntityManagerFactory().createEntityManager();

			T entity = em.find(clazz, key);

			if (entity == null)
				throw new NotFoundException();

			return entity;

		} catch (IllegalStateException exception){
			
			////logManager.logError(exception);

			throw new SearchException();

		} catch (IllegalArgumentException exception) {

			////logManager.logError(exception);

			throw new SearchException();

		} catch (HibernateException exception) {

			////logManager.logError(exception);

		    throw new SearchException();

		} finally {

			if (em != null)
				em.close();
			
			Date finish = new Date();
			long result = finish.getTime() - init.getTime() ;
			//logManager.logInfo(new LogInfo(DML.DELETE,TypeNamedGeneric.takeNameClass(clazz.getName()),"find hibernate", result, "buscar()").toString());
		}

	}

	@Override
	public <T> T consultaUnica(@Nonnull String namedQuery,
			@Nonnull Map<String, Object> params, @Nonnull Class<T> clazz)
			throws SearchException, BadResultException, NotFoundException,
			IllegalArgumentException {

		Date init = new Date();
		checkArgument(namedQuery != null);
		checkArgument(params != null);
		checkArgument(clazz != null);

		checkParams(params);

		EntityManager em = null;

		try {
			em = read.getEntityManagerFactory().createEntityManager();

			Query query = em.createNamedQuery(namedQuery);

			for (String key : params.keySet()) {

				Object value = params.get(key);

				checkArgument(value != null);

				query.setParameter(key, value);
			}

			Object result = query.getSingleResult();

			return clazz.cast(result);

		} catch (QueryTimeoutException exception) {
			
			////logManager.logError(exception);

			throw new SearchException();

		} catch (NonUniqueResultException exception) {
			
			////logManager.logError(exception);

			throw new BadResultException();

		} catch (NoResultException exception) {

			////logManager.logError(exception);

			throw new NotFoundException();

		} catch (ClassCastException exception) {

			////logManager.logError(exception);

			throw new BadResultException();

		} catch (IllegalStateException exception) {
			
			////logManager.logError(exception);

			throw new SearchException();

		} catch (HibernateException exception) {

			////logManager.logError(exception);
			
			throw new SearchException();

		} finally {

			if (em != null)
				em.close();
			
			Date finish = new Date();
			long result = finish.getTime() - init.getTime() ;
			//logManager.logInfo(new LogInfo(DML.SELECT,TypeNamedGeneric.takeNameClass(clazz.getName()),namedQuery.toString(), result, "consultaUnica(namedQuery, params, clazz)").toString());


		}

	}

	/*
	 * Con generics no es posible hacer un casteo seguro:
	 * 
	 * ArrayList<String> values = new ArrayList<>(); values.add("aaaaaaaaaa");
	 * 
	 * List<Double> doubles = cast(Double.class, values); doubles.add(10D);
	 * 
	 * static <T> List<T> cast(Class<T> clazz, Object object){ return (List<T>)
	 * object; }
	 * 
	 * El código falla solo si se obtiene un elemento de la lista y se se llaman
	 * métodos que no pertenecen al tipo:
	 * 
	 * doubles.get(0).isInfinite();
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> consultar(@Nonnull String namedQuery, @Nonnull Map<String, Object> params, @Nonnull Class<T> clazz)
			throws SearchException, IllegalArgumentException {

		Date init = new Date();
		
		checkArgument(namedQuery != null);
		checkArgument(params != null);
		checkArgument(clazz != null);

		checkParams(params);

		EntityManager em = null;

		try {
			em = read.getEntityManagerFactory().createEntityManager();

			Query query = em.createNamedQuery(namedQuery);

			for (String key : params.keySet()) {

				Object value = params.get(key);

				checkArgument(value != null);

				query.setParameter(key, value);
			}

			List<T> results = (List<T>) query.getResultList();

			return results;
		} catch (QueryTimeoutException exception) {
			
			////logManager.logError(exception);

			throw new SearchException();

		} catch (IllegalStateException exception) {

			////logManager.logError(exception);

			throw new SearchException();

		} catch (HibernateException exception) {

			////logManager.logError(exception);

			throw new SearchException();

		} finally {

			if (em != null)
				em.close();
			
			Date finish = new Date();
			long result = finish.getTime() - init.getTime() ;
			//logManager.logInfo(new LogInfo(DML.SELECT,TypeNamedGeneric.takeNameClass(clazz.getName()),namedQuery.toString(), result, "consultar(namedQuery, params, clazz)").toString());


		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> consultar(@Nonnull String namedQuery,
			@Nonnull Integer limite, @Nonnull Map<String, Object> params,
			@Nonnull Class<T> clazz) throws SearchException,
			IllegalArgumentException {

		Date init = new Date();
		checkArgument(namedQuery != null);
		checkArgument(params != null);
		checkArgument(clazz != null);
		checkArgument(limite != null);

		checkParams(params);

		EntityManager em = null;

		try {
			em = read.getEntityManagerFactory().createEntityManager();

			Query query = em.createNamedQuery(namedQuery);

			for (String key : params.keySet()) {

				Object value = params.get(key);

				checkArgument(value != null);

				query.setParameter(key, value);
			}

			query.setMaxResults(limite);

			List<T> results = (List<T>) query.getResultList();

			return results;
		} catch (QueryTimeoutException exception) {
			
			////logManager.logError(exception);

			throw new SearchException();

		} catch (IllegalStateException exception) {

			////logManager.logError(exception);

			throw new SearchException();

		} catch (HibernateException exception) {

			////logManager.logError(exception);

			throw new SearchException();

		} finally {

			if (em != null)
				em.close();
			
			
			Date finish = new Date();
			long result = finish.getTime() - init.getTime() ;
			//logManager.logInfo(new LogInfo(DML.SELECT,TypeNamedGeneric.takeNameClass(clazz.getName()),namedQuery.toString(), result, "consultar(namedQuery,limite, params, clazz)").toString());


		}

	}

	@Override
	public <T> List<T> consultar(@Nonnull String namedQuery,
			@Nonnull Class<T> clazz) throws SearchException,
			IllegalArgumentException {
		Date init = new Date();
		checkArgument(namedQuery != null);
		checkArgument(clazz != null);

		EntityManager em = null;

		try {
			em = read.getEntityManagerFactory().createEntityManager();

			Query query = em.createNamedQuery(namedQuery);

			return (List<T>) query.getResultList();
			
		} catch (QueryTimeoutException exception) {
			
			////logManager.logError(exception);

			throw new SearchException();

		} catch (IllegalStateException exception) {
			
			////logManager.logError(exception);

			throw new SearchException();

		} catch (HibernateException exception) {

			////logManager.logError(exception);

			throw new SearchException();

		} finally {

			if (em != null)
				em.close();
			
			Date finish = new Date();
			long result = finish.getTime() - init.getTime() ;
			//logManager.logInfo(new LogInfo(DML.SELECT,TypeNamedGeneric.takeNameClass(clazz.getName()),namedQuery.toString(), result, "consultar(namedQuery,clazz)").toString());

		}

	}

	private void checkParams(@Nonnull Map<String, Object> params)
			throws IllegalArgumentException {

		for (String key : params.keySet()) {
			Object value = params.get(key);

			checkArgument(value != null, "El parametro %s es null", key);
		}

	}

	/*
	 * ¿Que entidades no usan operaciones básicas, ej: usan transacciones? puede
	 * * haber conflictos con la separación de lecturas y escrituras.
	 */
	@Override
	public void callTransaction(@Nonnull TransactionManager transactionManger)
			throws PersistenceException {
		Date init = new Date();
		EntityManager em = null;

		try {

			checkArgument(transactionManger != null);

			em = write.getEntityManagerFactory().createEntityManager();

			em.getTransaction().begin();

			transactionManger.doTransaction(em);

			em.getTransaction().commit();

		} catch (IllegalStateException exception) {

			////logManager.logError(exception);
			
			throw new PersistenceException();

		} catch (EntityExistsException exception) {

			////logManager.logError(exception);
			
			throw new PersistenceException();

		} catch (QueryTimeoutException exception) {

			////logManager.logError(exception);
			
			throw new SearchException();

		} catch (NonUniqueResultException exception) {

			////logManager.logError(exception);
			
			throw new BadResultException();

		} catch (NoResultException exception) {
			
			////logManager.logError(exception);

			throw new NotFoundException();

		} catch (ClassCastException exception) {
			
			////logManager.logError(exception);

			throw new BadResultException();

		} catch (IllegalArgumentException exception) {
	
			////logManager.logError(exception);
			
			throw new PersistenceException();

		} catch (javax.persistence.PersistenceException exception) {
		
			if (em != null && em.getTransaction().isActive())
				em.getTransaction().rollback();

			////logManager.logError(exception);
			
			throw new PersistenceException();

		} catch (HibernateException exception) {

			if (em != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			
			////logManager.logError(exception);

			throw new PersistenceException();

		} finally {

			if (em != null)
				em.close();
			
			Date finish = new Date();
			long result = finish.getTime() - init.getTime() ;
			//logManager.logInfo(new LogInfo(DML.TRANSACTION,null,null, result, "callTransaction(transactionManger)").toString());


		}

	}

	private @Nonnull
	Object completeEntity(@Nonnull Object entity) {

		if (!(entity instanceof BaseEntity))
			return entity;

		BaseEntity hariEntity = (BaseEntity) entity;

		Date now = new Date();

		hariEntity.setFechaCreacion(now);
		hariEntity.setFechaActualizacion(now);

		return hariEntity;
	}

}
