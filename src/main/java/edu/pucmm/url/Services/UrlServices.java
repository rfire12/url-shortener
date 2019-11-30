package edu.pucmm.url.Services;

import edu.pucmm.url.Entities.Url;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.awt.print.Pageable;
import java.util.List;

public class UrlServices extends DatabaseManagement<Url> {
    private static UrlServices instance;

    private UrlServices() {
        super(Url.class);
    }

    public static UrlServices getInstance() {
        if (instance == null)
            instance = new UrlServices();
        return instance;
    }

    public List<Url> getLatest() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from Url u order by u.createdAt");
        query.setMaxResults(5);
        return query.getResultList();
    }
}
