import java.security.SecureRandom;
import java.util.Base64;

public class SecretGenerator {

    public static void main(String[] args) {

        byte[] secret = new byte[32];

        new SecureRandom().nextBytes(secret);

        String generatedSecret =
                Base64.getEncoder().encodeToString(secret);

        System.out.println("JWT SECRET: " + generatedSecret);
    }
}