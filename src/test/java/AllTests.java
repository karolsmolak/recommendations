import integration.MovieControllerTest;
import integration.UsersControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unit.UserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UsersControllerTest.class,
        MovieControllerTest.class,
        UserServiceTest.class
})

public class AllTests {

}