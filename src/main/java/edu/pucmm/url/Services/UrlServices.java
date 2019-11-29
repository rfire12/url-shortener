package edu.pucmm.url.Services;

import edu.pucmm.url.Entities.Info;
import edu.pucmm.url.Entities.Url;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
}
