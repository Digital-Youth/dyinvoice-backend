package com.dyinvoice.backend.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsService userDetailsService;


    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURL = request.getRequestURI();
        String jwtToken = null;

/*        // Only attempt to get token if not on an exempted route
        if (!requestURL.contains("/login")
                && !requestURL.contains("/register")
) {

            try {
                jwtToken = getTokenFromRequest(request);
            } catch (ServletException e) {
                // Handle exception if necessary
            }

        }*/

        if (jwtToken != null && StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {
            String username = jwtTokenProvider.getEmail(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }



    private String getTokenFromRequest(HttpServletRequest request) throws ServletException {

        String bearerToken = request.getHeader("Authorization");

        if(bearerToken == null) {
            throw new ServletException("Authorization header is missing");
        }

        if(!StringUtils.hasText(bearerToken)) {
            throw new ServletException("Authorization header is empty");
        }

        if(!bearerToken.startsWith("Bearer ")) {
            throw new ServletException("Authorization header does not start with Bearer ");
        }

        return bearerToken.substring(7, bearerToken.length());
    }

}
