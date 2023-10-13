import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.Password;

public class TokenGenerator {
    public static String generateToken(String username) {
        String pw = "secureKey";
        Password password = Keys.password(pw.toCharArray());
        KeyAlgorithm<Password, Password> alg = Jwts.KEY.PBES2_HS512_A256KW;
        AeadAlgorithm enc = Jwts.ENC.A256GCM;
        return Jwts.builder().subject(username)
            .encryptWith(password, alg, enc)
            .compact();
    }
}
