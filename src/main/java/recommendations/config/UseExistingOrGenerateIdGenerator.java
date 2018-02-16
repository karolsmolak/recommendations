package recommendations.config;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IncrementGenerator;

import java.io.Serializable;

//Custom id generator, necessary to maintain consistency between .csv ids
public class UseExistingOrGenerateIdGenerator extends IncrementGenerator {
    @Override
    public Serializable generate(SessionImplementor session, Object object)
            throws HibernateException {
        Serializable id = session.getEntityPersister(null, object)
                .getClassMetadata().getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }
}