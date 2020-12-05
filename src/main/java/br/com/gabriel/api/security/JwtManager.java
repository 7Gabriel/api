package br.com.gabriel.api.security;

import br.com.gabriel.api.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class JwtManager {

    public LoginResponse createToken(String email, List<String> roles){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, SecurityConstants.JWT_EXP_DAYS);

        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(calendar.getTime())
                .claim(SecurityConstants.JWT_ROLE_KEY, roles)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.API_KEY)
                .compact();
        Long expireIn = calendar.getTimeInMillis();

        return LoginResponse.builder()
                .expireIn(expireIn)
                .token(token)
                .tokenProvider(SecurityConstants.JWT_PROVIDER).build();
    }

    public Claims parseToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.API_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

}
