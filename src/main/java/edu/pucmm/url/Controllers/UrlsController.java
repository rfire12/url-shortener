package edu.pucmm.url.Controllers;

import com.maxmind.geoip2.model.CountryResponse;
import edu.pucmm.url.Entities.Info;
import edu.pucmm.url.Entities.Url;
import edu.pucmm.url.Entities.User;
import edu.pucmm.url.Services.InfoServices;
import edu.pucmm.url.Services.UrlServices;
import edu.pucmm.url.Services.UsersServices;
import eu.bitwalker.useragentutils.UserAgent;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static spark.Spark.*;


public class UrlsController {
    public static void getRoutes() {
        get("/", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            String annonymousUser = request.cookie("ANONYMOUSUSER");
            List<Url> urls = user != null ? (List<Url>) user.getMyUrls() : UrlServices.getInstance().getMyAnnonymousUrl(annonymousUser);

            Map<String, Object> obj = new HashMap<>();
            obj.put("latest", urls);
            obj.put("latestSize", urls.size());
            obj.put("user", user);
            obj.put("host", request.url());
            return TemplatesController.renderFreemarker(obj, "main.ftl");
        });

        before("/info/:id", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            Boolean isAUserUrl = user != null ? UrlServices.getInstance().isAUserUrl(request.params("id"), user.getUid()) : null;
            if (user == null || isAUserUrl == false) {
                response.redirect("/");
            }
        });

        get("/info/:id", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            Url url = UrlServices.getInstance().findByShortVersion(request.params("id"));
            List<Info> infoByUrl = InfoServices.getInstance().getInfoListByUrl(request.params("id"));

            Map<String, Object> obj = new HashMap<>();
            obj.put("url", url);
            obj.put("date", url.getCreatedAt().toString().substring(0, 10));
            obj.put("protocol", request.scheme());
            obj.put("host", request.host());
            obj.put("user", user);
            obj.put("access", infoByUrl);
            obj.put("accessCount", infoByUrl.size());
            return TemplatesController.renderFreemarker(obj, "url-info.ftl");
        });

        get("/s/:id", (request, response) -> {
            Url url = UrlServices.getInstance().find(request.params("id"));
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));

            UserAgent userAgent = UserAgent.parseUserAgentString(request.headers("User-Agent"));
            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Info info = new Info(UUID.randomUUID().toString(), sqlDate, url, userAgent.getBrowser().toString(), userAgent.getOperatingSystem().toString(), "Dominican Republic", request.ip());
            InfoServices.getInstance().create(info);
            response.redirect(url.getOriginalVersion());
            return "";
        });

        post("/shortify", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            String shortUrl = UUID.randomUUID().toString().split("-")[0];


            String annonymousUser = "";
            String newCookie = UUID.randomUUID().toString();
            String userCookie = request.cookie("ANONYMOUSUSER");

            if (userCookie == null && user == null) { //If it's a new user and not a registered one
                annonymousUser = newCookie;
            } else if (userCookie != null && user == null) {
                annonymousUser = userCookie;
            }


            Url url = new Url(shortUrl, request.queryParams("url"), "", user, annonymousUser);
            UrlServices.getInstance().create(url);

            if (user != null) {
                List<Url> myUrls = user.getMyUrls();
                myUrls.add(url);
                user.setMyUrls(myUrls);
                UsersServices.getInstance().update(user);
            } else if (userCookie == null) {
                response.cookie("ANONYMOUSUSER", newCookie, 604800);
            }
            response.redirect("/");
            return "";
        });

        post("/s/:id/delete", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            obj.put("user", user);
            String url = request.params("id");

            UrlServices.getInstance().delete(url);

//            if (UrlServices.getInstance().find(url) != null) {
//                UrlServices.getInstance().delete(url);
//                obj.put("type", "success");
//                obj.put("alert", "URL deleted!");
//            } else {
//                obj.put("type", "danger");
//                obj.put("alert", "URL not found");
//            }

            if (user.isAdmin())
                response.redirect("/urls");
            else
                response.redirect("/");
            return "";
        });

        get("/urls", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("urls", UrlServices.getInstance().findAll());

            return TemplatesController.renderFreemarker(obj, "urls.ftl");
        });
    }
}
