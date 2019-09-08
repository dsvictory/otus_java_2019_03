package ru.otus.homework.hibernate;

import org.hibernate.Transaction;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ORMTemplateImpl<T> implements ORMTemplate<T> {

	private long lastInsertedId = 0;
	
	private SessionFactory sessionFactory;
	
	public ORMTemplateImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public T getEntity(Class<T> entityClass, long id) {
		T selectedEntity = null;
		
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
				
			selectedEntity = session.get(entityClass, id);
			
			transaction.commit();
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

	@Override
	public List<T> getAll(Class<T> entityClass) {
		EntityManager entityManager = sessionFactory.createEntityManager();

        List<T> selectedEntities = entityManager.createQuery(
                "select e from " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
        
        return selectedEntities;
	}

}
