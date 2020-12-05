package br.com.gabriel.api.security;

import br.com.gabriel.api.constants.SecurityConstants;
import br.com.gabriel.api.resources.exception.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(token == null || !token.startsWith(SecurityConstants.JWT_PROVIDER)){
            errorResponse(request, response, SecurityConstants.JWT_INVALID_MSG, filterChain);
        }

        try {
            token = token.replace(SecurityConstants.JWT_PROVIDER, "");
            Claims claims = new JwtManager().parseToken(token);
            String email = claims.getSubject();
            List<String> roles = (List<String>) claims.get(SecurityConstants.JWT_ROLE_KEY);
            List<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });

            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (Exception e){
            errorResponse(request, response, e.getMessage(), filterChain);
        }

        filterChain.doFilter(request, response);

    }

    private void errorResponse(HttpServletRequest request, HttpServletResponse response, String errorMessage, FilterChain chain) throws IOException, ServletException {
        ApiError error = getApiError(errorMessage);
        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        var apiErrorResponse = mapper.writeValueAsString(error);

        writer.write(apiErrorResponse);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        return;
    }

    private ApiError getApiError(String errorMessage) {
        return ApiError.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .msg(errorMessage)
                    .date(LocalDateTime.now()).build();
    }
}
