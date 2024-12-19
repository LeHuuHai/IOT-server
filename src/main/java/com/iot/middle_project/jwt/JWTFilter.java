package com.iot.middle_project.jwt;

import com.iot.middle_project.model.MUser;
import com.iot.middle_project.service.MUserDetailService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private MUserDetailService mUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // JWT Filter khong tu dong bo qua endpoint cong khai
        String uri = request.getRequestURI();
        if (uri.startsWith("/auth/")) {
            filterChain.doFilter(request, response); // Bỏ qua xác thực và cho phép truy cập
            return;
        }

        try{
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            String type = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
                type = jwtService.extractType(token);
            }
            if (username != null && type.equals("access") && SecurityContextHolder.getContext().getAuthentication() == null) {
                MUser mUser = (MUser) mUserDetailService.loadUserByUsername(username);
                if (jwtService.validToken(token, mUser)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(mUser, null, mUser.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        }catch (JwtException exception) {
            response.setStatus(401);
            response.getWriter().write(exception.getMessage());
        }
    }
}
