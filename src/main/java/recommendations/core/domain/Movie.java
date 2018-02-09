package recommendations.core.domain;

import java.util.concurrent.ThreadLocalRandom;

public class Movie {

    private int numberOfFeatures;

    private Integer id;
    private String title;
    private String genre;

    private double[] features;

    public Movie(Integer id, String title, String genre, int numberOfFeatures) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.numberOfFeatures = numberOfFeatures;

        features = new double[numberOfFeatures];
        for (int i = 0 ; i < numberOfFeatures ; i++) {
            features[i] = ThreadLocalRandom.current().nextDouble(0, 1);
        }
    }

    public Integer getId() {
        return id;
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
