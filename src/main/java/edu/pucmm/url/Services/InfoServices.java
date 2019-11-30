package edu.pucmm.url.Services;

import edu.pucmm.url.Entities.Info;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class InfoServices extends DatabaseManagement<Info> {
    private static InfoServices instance;

    private InfoServices() {
        super(Info.class);
    }

    public static InfoServices getInstance() {
        if (instance == null)
            instance = new InfoServices();
        return instance;
    }

    public List<Info> getInfoListByUrl(String shortVersion){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select i from Info i where i.url.shortVersion = :url");
        query.setParameter("url", shortVersion);
        List<Info> list = query.getResultList();
        return list;
    }

    public int getCountByBrowser(String url, String browser) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select i from Info i where i.shortVersion = :url and i.browser = :browser");
        query.setParameter("url", url);
        query.setParameter("browser", url);
        return query.getResultList().size();
    }

    public int getCountByOS(String url, String os) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select i from Info i where i.shortVersion = :url and i.os = :os");
        query.setParameter("url", url);
        query.setParameter("os", os);
        return query.getResultList().size();
    }

    public int getCountByCountry(String url, String country) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select i from Info i where i.shortVersion = :url and i.country = :country");
        query.setParameter("url", url);
        query.setParameter("country", country);
        return query.getResultList().size();
    }
}
