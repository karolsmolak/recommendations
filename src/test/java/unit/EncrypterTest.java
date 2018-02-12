package unit;

import config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import recommendations.infrastructure.encrypter.IEncrypter;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
public class EncrypterTest {
    @Autowired
    IEncrypter _encrypter;

    @Test
    public void consistencyCheck() throws Exception {

        assertEquals(_encrypter.GetHash("abcd", "adad"), _encrypter.GetHash("abcd", "adad"));
    }

}
