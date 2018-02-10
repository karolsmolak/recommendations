package recommendations.core.domain;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class User {

    private int numberOfFeatures;

    private Integer id;

    private String email;
    private String username;
    private String password;
    private List<Double> features;

    private List<Rating> userRatings = new LinkedList<>();

    public User(String email, String username, String password, int numberOfFeatures) {
        this.email = email;
        this.username = username;
        this.password = password;

        this.numberOfFeatures = numberOfFeatures;

        features = new ArrayList<>();
        for (int i = 0 ; i < numberOfFeatures ; i++) {
            features.add(ThreadLocalRandom.current().nextDouble(0, 1));
        }
    }

    public void updateRating(Movie movie, Integer rating) {
        userRatings.add(new Rating(movie, rating));
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public Integer getId(){
        return id;
    }

    public Double getFeature(int i){
        return features.get(i);
    }

    public void setFeature(int index, double value) {
        this.features.set(index, value);
    }

    @Override
    public String toString() {
        return "User{" +
                "numberOfFeatures=" + numberOfFeatures +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", features=" + features +
                ", userRatings=" + userRatings +
                '}';
    }
}
