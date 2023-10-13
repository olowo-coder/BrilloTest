import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.Password;

public class TokenVerifier {
    public static String verifyToken(String token) {
        try {
            String pw = "secureKey";
            Password password = Keys.password(pw.toCharArray());
            String username = Jwts.parser().decryptWith(password)
                .build().parseEncryptedClaims(token).getPayload().getSubject();

            System.out.println(username);
            return "Verification pass";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Verification fails";
        }
    }
}

