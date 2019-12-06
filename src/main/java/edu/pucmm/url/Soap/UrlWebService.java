package edu.pucmm.url.Soap;

import edu.pucmm.url.Entities.Url;
import edu.pucmm.url.Entities.User;
import edu.pucmm.url.Services.UsersServices;

import javax.jws.WebMethod;
import javax.jws.WebService;
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

//    @WebMethod
//    public Url getShortUrl(String original, String username) {
//        User user = UsersServices.getInstance().findByUsername(username);
//        String shortUrl = UUID.randomUUID().toString().split("-")[0];
//        Url url = new Url(shortUrl, original, "", user, "");
//        return url;
//    }
}
