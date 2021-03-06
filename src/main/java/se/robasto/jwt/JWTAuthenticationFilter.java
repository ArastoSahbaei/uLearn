package se.robasto.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import se.robasto.model.LoginModel;
import se.robasto.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String TOKEN_PREFIX     = "Bearer ";
    public static final String HEADER_STRING    = "Authorization";

    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    @Autowired
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
        this.userService = ctx.getBean(UserService.class);
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        System.out.println("attemptAuthentication");
        try {
            LoginModel login = new ObjectMapper().readValue(
                    req.getInputStream(),LoginModel.class);
            req.getInputStream().close();

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getUsername(),
                            login.getPassword()
                    )
            );

        } catch (BadCredentialsException | IOException | InternalAuthenticationServiceException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        String username = ((User) auth.getPrincipal()).getUsername();
        se.robasto.entity.User user = userService.FindUserByUserName(username);

        String role = user.getRole();//"test" ;//authorityService.getUserRole(username_email);


        String token = JWTUtility.buildJWTToken(username, role);
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}