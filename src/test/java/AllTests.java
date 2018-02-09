import integration.UsersControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unit.UserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UsersControllerTest.class, //test case 1
        UserServiceTest.class     //test case 2
})

public class AllTests {

}