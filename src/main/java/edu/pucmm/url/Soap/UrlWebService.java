package edu.pucmm.url.Soap;

import edu.pucmm.url.Entities.Url;
import edu.pucmm.url.Entities.User;
import edu.pucmm.url.Services.UrlServices;
import edu.pucmm.url.Services.UsersServices;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@WebService
public class UrlWebService {
    @WebMethod
    public String helloWorld() {
        return "Hello World";
    }

    @WebMethod
    public List<Url> getUrlsByUser(String username, String password) {
        User user = UsersServices.getInstance().findByUsername(username);
        if (user.getPassword() == password)
            return user.getMyUrls();
        else
            return null;
    }

    @WebMethod
    public Url getShortUrl(String original, String username, String password) {
        User user = UsersServices.getInstance().findByUsername(username);
        if (user.getPassword() == password) {
            String shortUrl = UUID.randomUUID().toString().split("-")[0];
            Url url = new Url(shortUrl, original, "", user, "");

            /* Image base 64*/
            String linkPreviewAPI = "https://api.linkpreview.net/?key=5de82b007f6d0ee5d57044e005d0f8104161e20b42286&q=" + url.getOriginalVersion();
            HttpResponse<JsonNode> linkPreviewResult = Unirest.get(linkPreviewAPI)
                    .asJson();
            String image = linkPreviewResult.getBody().getObject().getString("image");
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());

            url.setImageBase(base64Image);

            UrlServices.getInstance().create(url);

            List<Url> myUrls = user.getMyUrls();

            myUrls.add(url);
            user.setMyUrls(myUrls);
            UsersServices.getInstance().update(user);

            return url;
        } else {
            return null;
        }
    }
}
