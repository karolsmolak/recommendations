package recommendations.infrastructure.dto;

public class MovieDto {
    private Integer id;
    private String title;
    private String genre;

    public MovieDto() {
    }

    public MovieDto(Integer id, String title, String genre) {
        this.id = id;
        this.genre = genre;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}