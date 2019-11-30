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

    public Url findByShortVersion(String myUrl) {
        Url url = null;
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from Url u where u.shortVersion = :url");
        query.setParameter("url", myUrl);
        List<Url> list = query.getResultList();
        if (list.size() > 0)
            url = list.get(0);
        return url;
    }



    public List<Url> getMyAnnonymousUrl(String cookie) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from Url u where u.anonymousUser =: cookie  order by u.createdAt");
        query.setParameter("cookie", cookie);
        query.setMaxResults(5);
        return query.getResultList();
    }
}
