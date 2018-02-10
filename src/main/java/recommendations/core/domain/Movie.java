package recommendations.core.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Entity
public class Movie {

    private int numberOfFeatures;

    @Id
    private Long id;
    private String title;
    private String genre;

    private List<Double> features;

    public Movie() {
    }

    public Movie(Long id, String title, String genre, int numberOfFeatures) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.numberOfFeatures = numberOfFeatures;

        features = new ArrayList<>();
        for (int i = 0 ; i < numberOfFeatures ; i++) {
            features.add(ThreadLocalRandom.current().nextDouble(0, 1));
        }
    }

    public Long getId() {
        return id;
    }

    public void setFeature(int i, double val) {
        features.set(i, val);
    }

    public String getTitle() {
        return title;
    }

    public String getGenre(){
        return genre;
    }

    public Double getFeature(int i){
        return features.get(i);
    }

}
