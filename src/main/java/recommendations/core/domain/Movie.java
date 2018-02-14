package recommendations.core.domain;

import javax.persistence.*;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String genre;

    @Column(length = 20923)
    private double[] features;

    public Movie() {
    }

    public Movie(Integer id, String title, String genre, int numberOfFeatures) {
        this.id = id;
        this.title = title;
        this.genre = genre;

        features = new double[numberOfFeatures];
        for (int i = 0 ; i < numberOfFeatures ; i++) {
            features[i] =  (ThreadLocalRandom.current().nextDouble(0, 1));
        }
    }

    public Integer getId() {
        return id;
    }

    public void setFeature(int i, double val) {
        features[i] = val;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre(){
        return genre;
    }

    public Double getFeature(int i){
        return features[i];
    }
}
