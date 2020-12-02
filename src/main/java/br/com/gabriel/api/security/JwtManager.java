package br.com.gabriel.api.security;

import br.com.gabriel.api.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class JwtManager {

    public String createToken(String email, List<String> roles){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, SecurityConstants.JWT_EXP_DAYS);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(calendar.getTime())
                .claim(SecurityConstants.JWT_ROLE_KEY, roles)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.API_KEY)
                .compact();
    }

}