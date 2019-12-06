package edu.pucmm.url.Soap;

import edu.pucmm.url.Entities.Url;
import edu.pucmm.url.Entities.User;
import edu.pucmm.url.Services.UrlServices;
import edu.pucmm.url.Services.UsersServices;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@WebService
public class UrlWebService {
    @WebMethod
    public String helloWorld() {
        return "Hello World";
    }

    @WebMethod
    public List<Url> getUrlsByUser(String username) {
        User user = UsersServices.getInstance().findByUsername(username);
        return user.getMyUrls();
    }

    @WebMethod
    public Url getShortUrl(String original, String username) {
        User user = UsersServices.getInstance().findByUsername(username);
        String shortUrl = UUID.randomUUID().toString().split("-")[0];
        Url url = new Url(shortUrl, original, "", user, "");
        UrlServices.getInstance().create(url);
        List<Url> myUrls = user.getMyUrls();
        myUrls.add(url);
        user.setMyUrls(myUrls);
        UsersServices.getInstance().update(user);
        return url;
    }
}
