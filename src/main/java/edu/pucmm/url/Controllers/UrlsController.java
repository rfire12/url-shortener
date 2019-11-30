package edu.pucmm.url.Controllers;

import edu.pucmm.url.Entities.Url;
import edu.pucmm.url.Entities.User;
import edu.pucmm.url.Services.UrlServices;
import edu.pucmm.url.Services.UsersServices;

import java.util.*;

import static spark.Spark.*;

public class UrlsController {
    public static void getRoutes() {
        get("/", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User)request.session().attribute("user")));
            String annonymousUser = request.cookie("ANONYMOUSUSER");
            List<Url> urls = user != null ? user.getMyUrls() : UrlServices.getInstance().getMyAnnonymousUrl(annonymousUser);

            Map<String, Object> obj = new HashMap<>();
            obj.put("latest", urls);
            obj.put("latestSize", urls.size());
            obj.put("user", user);
            return TemplatesController.renderFreemarker(obj, "main.ftl");
        });

        post("/shortify", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User)request.session().attribute("user")));
            String shortUrl = UUID.randomUUID().toString().split("-")[0];
            String annonymousUser = "";
            String newCookie = UUID.randomUUID().toString();
            String userCookie = request.cookie("ANONYMOUSUSER");

            if(userCookie == null && user == null){ //If it's a new user and not a registered one
                annonymousUser = newCookie;
            }else if(userCookie != null && user == null ){
                annonymousUser = userCookie;
            }
            System.out.println(annonymousUser);
            System.out.println(user);

            Url url = new Url(shortUrl, request.queryParams("url"), "", "", "", user, annonymousUser);
            UrlServices.getInstance().create(url);

            if(user != null){
                List<Url> myUrls = user.getMyUrls();
                myUrls.add(url);
                user.setMyUrls(myUrls);
                UsersServices.getInstance().update(user);
            }else if(userCookie == null){
                response.cookie("ANONYMOUSUSER", newCookie, 604800);
            }
            response.redirect("/");
            return "";
        });


    }
}
