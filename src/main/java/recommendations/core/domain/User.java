package recommendations.core.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Entity
public class User {

    @Id
    @GeneratedValue(generator="myGenerator")
    @GenericGenerator(name="myGenerator", strategy="recommendations.config.UseExistingOrGenerateIdGenerator")
    @Column(unique=true, nullable=false)
    private Integer id;

    private String email;
    private String username;
    private String password;

    @Column(length = 20923)
    private double[] features;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> userRatings = new LinkedList<>();

    public User() {}

    public User(Integer id, String email, String username, String password, int numberOfFeatures) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = id;

        features = new double[numberOfFeatures];
        for (int i = 0 ; i < numberOfFeatures ; i++) {
            features[i] = (ThreadLocalRandom.current().nextDouble(0, 1));
        }
    }


    public User(String email, String username, String password, int numberOfFeatures) {
        this.email = email;
        this.username = username;
        this.password = password;

        features = new double[numberOfFeatures];
        for (int i = 0 ; i < numberOfFeatures ; i++) {
            features[i] = (ThreadLocalRandom.current().nextDouble(0, 1));
        }
    }

    public void updateRating(Movie movie, Integer rating) {
        userRatings = userRatings.stream().filter((Rating r) -> !r.getMovie().equals(movie))
                .collect(Collectors.toList());

        if (rating != null) {
            userRatings.add(new Rating(movie, rating));
        }
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
}
