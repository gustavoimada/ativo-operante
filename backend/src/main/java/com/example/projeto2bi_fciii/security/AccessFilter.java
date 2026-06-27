package com.example.projeto2bi_fciii.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AccessFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getMethod().equalsIgnoreCase("OPTIONS"))
        {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String rotaDestino = request.getRequestURI();
        String token = getToken(request);

        if(token == null || !JWTTokenProvider.verifyToken(token))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().write("Acesso nao autorizado".getBytes());
            return;
        }

        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        if(claims == null)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().write("Token invalido".getBytes());
            return;
        }

        String nivel = claims.get("nivel").toString();
        String email = claims.getSubject();

        if(rotaDestino.startsWith(request.getContextPath() + "/apis/adm") && !nivel.equals("ADM"))
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getOutputStream().write("Somente administrador pode acessar este endpoint".getBytes());
            return;
        }

        if(rotaDestino.startsWith(request.getContextPath() + "/apis/cidadao") && !nivel.equals("CIDADAO"))
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getOutputStream().write("Somente cidadao pode acessar este endpoint".getBytes());
            return;
        }

        request.setAttribute("usuarioEmail", email);
        request.setAttribute("nivel", nivel);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getToken(HttpServletRequest request)
    {
        String authorization = request.getHeader("Authorization");

        if(authorization == null || authorization.isBlank())
            return null;

        if(authorization.startsWith("Bearer "))
            return authorization.substring(7);

        return authorization;
    }
}
