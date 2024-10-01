package com.uca.JWT;

import com.uca.entity.UserEntity;
import com.uca.core.UserCore;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Login
{
    private final static String TOKEN = "QVAlKTzo1zW9VwfGvJtrFZiSOzzEzEyb4Q4qdYIYnKqhd4l7Iasgq8LbesvH01Jk8kA49HNt9fq4M4Lpjpjvysyso7egZNlmHSU";

    public static String doLogin(UserEntity entity) throws IllegalArgumentException
    {

        if (entity.getIdentifier() == null || entity.getIdentifier().isEmpty())
        {
            throw new IllegalArgumentException("name could not be null");
        }
        if (entity.getPassword() == null || entity.getPassword().isEmpty())
        {
            throw new IllegalArgumentException("password could not be null");
        }

        UserEntity user;
        try
        {
            user = UserCore.getByIdentifier(entity.getIdentifier());

        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("User does not exists.");

        }

        String dataBasePassword = user.getPassword();
        String inputPassword = entity.getPassword();
        if (!(dataBasePassword.equals(inputPassword)))
        {
            throw new IllegalArgumentException("Invalid password");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("uuid", user.getId());
        map.put("id", user.getId());
        map.put("emitter", user.getIdentifier());

        Map<String, Object> header = new HashMap<>();
        header.put("kid", user.getId());

        return Jwts.builder()
                .setClaims(map)
                .setId(UUID.randomUUID().toString())
                .setHeader(header)
                .setHeaderParam("kid", user.getId())
                .setSubject(user.getIdentifier())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 20))
                .signWith(SignatureAlgorithm.HS512, TOKEN)
                .compact();
    }

    public static UserEntity introspect(String token)
    {
        if (token == null || token.isEmpty())
        {
            return null;
        }
        try
        {

            Claims claims = Jwts.parser().setSigningKey(TOKEN).parseClaimsJws(token).getBody();
            UserEntity entity = new UserEntity();
            entity.setIdentifier(claims.get("emitter", String.class));
            entity.setId(claims.get("uuid", Integer.class));

            return entity;

        }
        catch (Exception e)
        {
            return null;
        }

    }
}
