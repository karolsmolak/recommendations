package recommendations.infrastructure.encrypter;

public interface IEncrypter {
    String getSalt() throws IllegalArgumentException;
    String GetHash(String value, String salt) throws Exception;
}