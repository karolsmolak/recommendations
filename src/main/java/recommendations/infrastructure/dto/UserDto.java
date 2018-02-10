package recommendations.infrastructure.dto;

import recommendations.core.domain.Rating;

import java.util.List;

public class UserDto {

    private String email;
    private String username;
    private List<Rating> userRatings;

    public UserDto(){}

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
}
