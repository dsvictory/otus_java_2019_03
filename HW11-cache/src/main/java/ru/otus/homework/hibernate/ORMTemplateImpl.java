package ru.otus.homework.hibernate;

import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ru.otus.homework.cache.*;

public class ORMTemplateImpl<T> implements ORMTemplate<T> {

	private long lastInsertedId = 0;
	
	private SessionFactory sessionFactory;
	
	private CacheEngine<Long, T> cache = new CacheEngineImpl<Long, T>(5, 0, 0, true);
	
	public ORMTemplateImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public T getEntity(Class<T> entityClass, long id) {
		T selectedEntity = cache.get(id);
		if (selectedEntity != null) { return selectedEntity; }
		
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();

			selectedEntity = session.get(entityClass, id);
			
			transaction.commit();
			
			cache.put(id, selectedEntity);
        }
		catch (Exception ex) {
			transaction.rollback();
			System.out.println(ex.getMessage());
		}
		
		return selectedEntity;
	}

	@Override
	public boolean saveEntity(T entity) {
		boolean isAccess = false;
		
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();

			lastInsertedId = (long)session.save(entity);
			
			transaction.commit();
			
			isAccess = true;
        }
		catch (Exception ex) {
			transaction.rollback();
			System.out.println(ex.getMessage());
		}
		
		return isAccess;
	}
	
	public long getLastInsertedId() {
		return lastInsertedId;
	}

}
