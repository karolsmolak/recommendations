package recommendations.infrastructure.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import recommendations.core.domain.Movie;
import recommendations.core.repositories.IMovieRepository;

import java.util.List;

@Repository
@Primary
public class MovieRepository implements IMovieRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Movie movie) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(movie);
        transaction.commit();
    }

    @Override
    public Movie findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Movie result = sessionFactory.getCurrentSession().get(Movie.class, id);
        transaction.commit();
        return result;
    }

    @Override
    public List<Movie> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from movie").list();
    }

    @Override
    public void addMany(List<Movie> movies) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        for (Movie movie : movies) {
            session.save(movie);
        }
        transaction.commit();
    }
}
