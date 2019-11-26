package edu.pucmm.url.Services;

import edu.pucmm.url.Entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UsersServices extends DatabaseManagement<User> {
    private static UsersServices instance;

    private UsersServices() {
        super(User.class);
    }

    public static UsersServices getInstance() {
        if (instance == null) {
            instance = new UsersServices();
        }
        return instance;
    }

    public User validateCredentials(String username, String password) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from User u where u.username = :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> list = query.getResultList();
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    public User findByObject(User user) {
        if (user != null) {
            EntityManager em = getEntityManager();
            Query query = em.createQuery("select u from User u where u.uid = :uid");
            query.setParameter("uid", user.getUid());
            List<User> list = query.getResultList();
            if (list.size() > 0)
                return list.get(0);
            else
                return null;
        } else
            return null;
    }
}
