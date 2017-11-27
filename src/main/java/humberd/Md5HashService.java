package humberd;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Md5HashService implements HashService {
    public String getHash(String message) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(message));
        return String.format("%032x", new BigInteger(1, md5.digest()));
    }
}
