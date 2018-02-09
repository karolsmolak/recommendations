package unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import recommendations.api.Application;
import recommendations.core.repositories.IUserRepository;
import recommendations.infrastructure.dto.UserDto;
import recommendations.infrastructure.services.IUserService;
import recommendations.infrastructure.services.UserService;

import static junit.framework.TestCase.fail;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    IUserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Mock
    IUserRepository mockRepository;

    @Autowired
    IUserRepository userRepository;

    @Test
    public void registeringUserShouldInvokeAddOnRepository(){
        userService = new UserService(mockRepository, modelMapper);

        try{
            userService.register("user@mail", "username", "secret");
        } catch (Exception e) {
            fail("unexpected exception");
        }

        Mockito.verify(mockRepository, Mockito.times(1)).add(Matchers.anyObject());
    }

    @Test
    public void registeringExistingUserShouldThrowException(){
        userService = new UserService(userRepository, modelMapper);

        try{
            userService.register("user1@mail", "username", "secret");
        } catch (Exception e) {
            Mockito.verify(mockRepository, Mockito.never()).add(Matchers.anyObject());
            return;
        }

        fail("Exception was not thrown");
    }

    @Test
    public void getOnExistingUserShouldNotBeNull(){
        userService = new UserService(userRepository, modelMapper);

        userService.getById(1);
    }
}
