package recommendations.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recommendations.infrastructure.command.Login;
import recommendations.infrastructure.services.IUserService;

@RestController
@RequestMapping("/login")
public class LoginController {

    private IUserService _userService;

    @Autowired
    public LoginController(IUserService userService){
        _userService = userService;
    }

    @PostMapping
    public void post(@RequestBody Login login) {
    }

}
