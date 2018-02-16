package unit;

import config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import recommendations.core.repositories.IUserRepository;
import recommendations.infrastructure.encrypter.IEncrypter;
import recommendations.infrastructure.services.IUserService;
import recommendations.infrastructure.services.UserService;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
public class UserServiceTest {

    private IUserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Mock
    private
    IUserRepository mockRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IEncrypter _encrypter;

    @Test
    public void registeringUserShouldInvokeAddOnRepository(){
        userService = new UserService(mockRepository);

        try{
            userService.register("user@mail", "username", "secret");
        } catch (Exception e) {
            fail("unexpected exception");
        }

        Mockito.verify(mockRepository, Mockito.times(1)).add(Matchers.anyObject());
    }

    @Test
    public void registeringExistingUserShouldThrowException() {
        userService = new UserService(userRepository);

        try{
            userService.register("user1@mail.com", "user1", "secret");
        } catch (Exception e) {
            e.printStackTrace();
            Mockito.verify(mockRepository, Mockito.never()).add(Matchers.anyObject());
            return;
        }

        fail("Exception was not thrown");
    }

    @Test
    public void getOnExistingUserShouldNotBeNull(){
        userService = new UserService(userRepository);

        assertNotNull(userService.getByUsername("user1"));
    }

}
