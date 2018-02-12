package recommendations.infrastructure.dto;

import recommendations.core.domain.Rating;

import java.util.List;

public class UserDto {

    private String password;
    private String salt;

    private String email;
    private String username;
    private List<Rating> userRatings;

    public UserDto(){}

    public UserDto(String password, String salt, String email, String username, List<Rating> userRatings) {
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.username = username;
        this.userRatings = userRatings;
    }

    public UserDto(String email, String username, List<Rating> userRatings) {
        this.email = email;
        this.username = username;
        this.userRatings = userRatings;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
