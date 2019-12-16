package edu.pucmm.url.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.pucmm.url.Entities.Info;
import edu.pucmm.url.Entities.Url;
import edu.pucmm.url.Entities.User;
import edu.pucmm.url.Services.InfoServices;
import edu.pucmm.url.Services.UrlServices;
import edu.pucmm.url.Services.UsersServices;
import edu.pucmm.url.utils.Utils;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static spark.Spark.*;
public class RestApiController {

    private DecodedJWT jwt;

    public static void getRoutes() {
        before("/rest", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            if(user == null){
                response.redirect("/", 302);
            }
        });

        get("/rest", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            Map<String, Object> obj = new HashMap<>();
            obj.put("user", user);
            return TemplatesController.renderFreemarker(obj, "rest.ftl");
        });

        post("/generate-jwt", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            String token = null;
            System.out.println(request.queryParams("jwt-date"));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Date expirationDate = df.parse(request.queryParams("jwt-date"));
            try {
                Algorithm algorithm = Algorithm.HMAC256("KJFhJHNnHn1dsd433dofmK");
                token = JWT.create()
                        .withIssuer("auth0")
                        .withClaim("user", user.getUsername())
                        .withExpiresAt(expirationDate)
                        .sign(algorithm);
            } catch (JWTCreationException exception){
                token = null;
            }
            Map<String, Object> obj = new HashMap<>();
            obj.put("user", user);
            obj.put("jwt", token);
            obj.put("jwt_exp", JWT.decode(token).getExpiresAt().toString());
            obj.put("protocol", request.scheme());
            obj.put("host", request.host());
            return TemplatesController.renderFreemarker(obj, "rest.ftl");
        });

        before("/rest-api/v1/*", (request, response) -> {
            System.out.println(request.requestMethod());
            if(!request.requestMethod().equalsIgnoreCase("options")) {
                DecodedJWT jwt = Utils.getJwt(request.headers("Authorization"));
                if (jwt == null) {
                    halt(401, "{\"data\": \"Invalid token\"}");
                }
            }
        });


        path("/rest-api/v1", () -> {
            before("/*", (request, response) -> {
                if(!request.requestMethod().equalsIgnoreCase("options")) {
                    DecodedJWT jwt = Utils.getJwt(request.headers("Authorization"));
                    if (jwt == null) {
                        halt(401, "{\"data\": \"Invalid token\"}");
                    }
                }
            });

            get("/urls", (request, response) -> {
                DecodedJWT jwt = Utils.getJwt(request.headers("Authorization"));
                User user = UsersServices.getInstance().findByUsername(jwt.getClaim("user").asString());
                List<Url> myUrls = user.getMyUrls();
                Map<String, String> url;
                List<Map> urls = new ArrayList<>();

                for(Url myUrl: myUrls){
                    url = new HashMap<>();
                    url.put("original_version", myUrl.getOriginalVersion());
                    url.put("short_version", request.scheme() + "://" + request.host() + "/s/" + myUrl.getShortVersion());
                    url.put("link_use_count", Integer.toString(InfoServices.getInstance().getInfoListByUrl(myUrl.getShortVersion()).size()));

                    List<Info> urlInfo = InfoServices.getInstance().getInfoListByUrl(myUrl.getShortVersion());
                    String browsers = "";
                    String os = "";
                    String ips = "";

                    for(Info info: urlInfo){
                        browsers += info.getBrowser() + ", ";
                        os += info.getOs() + ", ";
                        ips += info.getIp() + ", ";
                    }

                    url.put("browsers", browsers);
                    url.put("OS", os);
                    url.put("IPs", ips);

                    urls.add(url);
                }
                Map<String, List> mapUrl = new HashMap<>();
                mapUrl.put("urls", urls);
                Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

                return gson.toJson(mapUrl);
            });

            post("/create", (request, response) -> {
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
                Map<String,String> data = gson.fromJson(request.body(), stringStringMap);
                DecodedJWT jwt = Utils.getJwt(request.headers("Authorization"));

                User user = UsersServices.getInstance().findByUsername(jwt.getClaim("user").asString());

                /* Shortify URL */
                String shortUrl = UUID.randomUUID().toString().split("-")[0];

                Url url = new Url(shortUrl, data.get("url"), "", user, null);
                UrlServices.getInstance().create(url);

                /* Adding URL to the user's list */
                List<Url> myUrls = user.getMyUrls();
                myUrls.add(url);
                user.setMyUrls(myUrls);
                UsersServices.getInstance().update(user);


                Map<String, String> mapUrl = new HashMap<>();
                mapUrl.put("shortened-url", request.scheme() + "://" + request.host() + "/s/" + shortUrl);
                mapUrl.put("createdAt", url.getCreatedAt().toString());


                // Stats
                List<Info> urlInfo = InfoServices.getInstance().getInfoListByUrl(url.getShortVersion());

                String browsers = "";
                String os = "";
                String ips = "";

                for(Info info: urlInfo){
                    browsers += info.getBrowser() + ", ";
                    os += info.getOs() + ", ";
                    ips += info.getIp() + ", ";
                }


                /* Image base 64*/
                String linkPreviewAPI = "https://api.linkpreview.net/?key=5dea9a9314d2f06769d64b132210e2c34c21362c8ed88&q=" + url.getOriginalVersion();
                HttpResponse<JsonNode> linkPreviewResult = Unirest.get(linkPreviewAPI)
                        .asJson();
                String image = linkPreviewResult.getBody().getObject().getString("image");
                String base64Image = Base64.getEncoder().encodeToString(image.getBytes());

                mapUrl.put("browsers", browsers);
                mapUrl.put("OS", os);
                mapUrl.put("IPs", ips);
                mapUrl.put("image", base64Image);
                mapUrl.put("imageTitle", linkPreviewResult.getBody().getObject().getString("title"));
                mapUrl.put("imageUrl", linkPreviewResult.getBody().getObject().getString("url"));
                gson = new GsonBuilder().setPrettyPrinting().create();
                return gson.toJson(mapUrl);

            });
        });
    }
}
