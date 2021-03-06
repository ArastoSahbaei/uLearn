package se.robasto.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import se.robasto.model.UserModel;

@Component
public class JwtValidator {


    private String secret = "volvo";

    public UserModel validate(String token) {

        UserModel userModel = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            userModel = new UserModel();

            userModel.setUserName(body.getSubject());
            userModel.setId(Long.parseLong((String) body.get("userId")));
            userModel.setRole((String) body.get("role"));
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return userModel;
    }
}
