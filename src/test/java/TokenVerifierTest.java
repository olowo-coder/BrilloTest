import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TokenVerifierTest {

    @Test
    public void testVerifyToken() {
        assertEquals("Verification fails", TokenVerifier.verifyToken("token"));
    }
}
