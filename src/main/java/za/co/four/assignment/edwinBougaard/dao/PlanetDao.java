package za.co.four.assignment.edwinBougaard.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.four.assignment.edwinBougaard.entity.Planet;

import java.util.List;


@Repository
@Transactional
public class PlanetDao {

    private SessionFactory sessionFactory;

    @Autowired
    public PlanetDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Planet vertex) {
        Session session = sessionFactory.getCurrentSession();
        session.save(vertex);
    }

    public void update(Planet vertex) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(vertex);
    }

    public int delete(String vertexId) {
        Session session = sessionFactory.getCurrentSession();
        String qry = "DELETE FROM vertex AS V WHERE V.vertexId = :vertexIdParameter";
        Query query = session.createQuery(qry);
        query.setParameter("vertexIdParameter", vertexId);

        return query.executeUpdate();
    }

    public Planet selectUnique(String vertexId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Planet.class);
        criteria.add(Restrictions.eq("vertexId", vertexId));

        return (Planet) criteria.uniqueResult();
    }

    public Planet selectUniqueByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Planet.class);
        criteria.add(Restrictions.eq("name", name));

        return (Planet) criteria.uniqueResult();
    }

    public List<Planet> selectAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Planet.class);
        List<Planet> vertices = (List<Planet>) criteria.list();

        return vertices;
    }
}
