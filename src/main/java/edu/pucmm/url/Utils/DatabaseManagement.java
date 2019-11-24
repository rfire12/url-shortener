package edu.pucmm.url.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.Field;
import java.util.List;

public class DatabaseManagement<T> {
    private static EntityManagerFactory emf;
    private Class<T> entityClass;

    public DatabaseManagement(Class<T> entityClass) {
        if (emf == null)
            emf = Persistence.createEntityManagerFactory("MyDatabase");
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private Object getFieldValue(T entity) {
        if (entity == null)
            return null;

        for (Field f : entity.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                try {
                    f.setAccessible(true);
                    Object fieldValue = f.get(entity);
                    return fieldValue;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public boolean create(T entity) {
        boolean ok = false;
        EntityManager em = getEntityManager();

        try {
            if (em.find(entityClass, getFieldValue(entity)) != null) {
                System.out.println("Entity already exists");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.getTransaction().begin();

        try {
            em.persist(entity);
            em.getTransaction().commit();
            ok = true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return ok;
    }

    public void update(T entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void delete(Object idEntity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            T entity = em.find(entityClass, idEntity);
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public T find(Object id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
            criteriaQuery.select(criteriaQuery.from(entityClass));
            return em.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
