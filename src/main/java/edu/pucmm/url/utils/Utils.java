package edu.pucmm.url.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public final class Utils {

    public static DecodedJWT getJwt(String token){
        DecodedJWT jwt;
        try {
            Algorithm algorithm = Algorithm.HMAC256("KJFhJHNnHn1dsd433dofmK");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            jwt = verifier.verify(token);
        } catch (Exception e){
            jwt = null;
        }
        return jwt;
    }

}
