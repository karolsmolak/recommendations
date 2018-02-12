package recommendations.infrastructure.encrypter;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

@Component
public class Encrypter implements IEncrypter {

    private static final Random random = new SecureRandom();
    private static final int deriveBytesIterationsCount = 1000;
    private static final int saltSize = 40;

    @Override
    public String getSalt() throws IllegalArgumentException {

        byte[] salt = new byte[saltSize];
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

    @Override
    public String GetHash(String value, String salt) throws Exception {
        if (value.isEmpty())
        {
            throw new IllegalArgumentException("Can not generate hash from an empty value.");
        }

        if (salt.isEmpty())
        {
            throw new IllegalArgumentException("Can not use an empty salt from hashing value.");
        }

        KeySpec spec = new PBEKeySpec(value.toCharArray(), salt.getBytes(), deriveBytesIterationsCount, saltSize);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }

}