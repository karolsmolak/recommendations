package recommendations.core.domain;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int numberOfFeatures;
    private String email;
    private String username;
    private String password;
    private String salt;

    @Column(length = 20923)
    private double[] features;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Rating> userRatings = new LinkedList<>();

    public User(Integer id, String email, String username, String password, String salt, int numberOfFeatures) {
        this.salt = salt;
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = id;
        this.numberOfFeatures = numberOfFeatures;

        features = new double[numberOfFeatures];
        for (int i = 0 ; i < numberOfFeatures ; i++) {
            features[i] = (ThreadLocalRandom.current().nextDouble(0, 1));
        }
    }


    public User(String email, String username, String password, String salt, int numberOfFeatures) {
        this.salt = salt;
        this.email = email;
        this.username = username;
        this.password = password;
        this.numberOfFeatures = numberOfFeatures;

        features = new double[numberOfFeatures];
        for (int i = 0 ; i < numberOfFeatures ; i++) {
            features[i] = (ThreadLocalRandom.current().nextDouble(0, 1));
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
        return features[i];
    }

    public void setFeature(int index, double value) {
        this.features[index] = value;
    }

    public String getSalt() {
        return salt;
    }
}
