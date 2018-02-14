package recommendations.infrastructure.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import recommendations.core.domain.Movie;
import recommendations.core.repositories.IMovieRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Primary
@Transactional
public class MovieRepository implements IMovieRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Movie movie) {
        entityManager.merge(movie);
    }

    @Override
    public Movie findById(Integer id) {
        return entityManager.find(Movie.class, id);
    }

    @Override
    public List<Movie> findAll() {
        return null;
    }

    @Override
    public void addMany(List<Movie> movies) {
        for (Movie movie : movies) {
            entityManager.merge(movie);
        }
    }
}
