import integration.LoginControllerTest;
import integration.MovieControllerTest;
import integration.UsersControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unit.EncrypterTest;
import unit.UserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UsersControllerTest.class,
        MovieControllerTest.class,
        LoginControllerTest.class,
        EncrypterTest.class,
        UserServiceTest.class
})

public class AllTests {

}