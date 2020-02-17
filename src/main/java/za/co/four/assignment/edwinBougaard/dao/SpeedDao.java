package za.co.four.assignment.edwinBougaard.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.four.assignment.edwinBougaard.entity.Speed;

import java.util.List;


@Repository
@Transactional
public class SpeedDao {

    private SessionFactory sessionFactory;

    @Autowired
    public SpeedDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void save(Speed edge) {
        Session session = sessionFactory.getCurrentSession();
        session.save(edge);
    }

    public void update(Speed edge) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(edge);
    }

    public int delete(long recordId) {
        Session session = sessionFactory.getCurrentSession();
        String qry = "DELETE FROM edge AS E WHERE E.recordId = :recordIdParameter";
        Query query = session.createQuery(qry);
        query.setParameter("recordIdParameter", recordId);

        return query.executeUpdate();
    }

    public Speed selectUnique(long recordId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Speed.class);
        criteria.add(Restrictions.eq("recordId", recordId));

        return (Speed) criteria.uniqueResult();
    }

    public long selectMaxRecordId() {
        long maxId = (Long) sessionFactory.getCurrentSession()
                .createCriteria(Speed.class)
                .setProjection(Projections.max("recordId")).uniqueResult();

        return maxId;
    }

    public List<Speed> edgeExists(Speed edge) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Speed.class);
        criteria.add(Restrictions.ne("recordId", edge.getRecordId()));
        criteria.add(Restrictions.eq("source", edge.getSource()));
        criteria.add(Restrictions.eq("destination", edge.getDestination()));
        List<Speed> edges = (List<Speed>) criteria.list();

        return edges;
    }

    public List<Speed> selectAllByRecordId(long recordId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Speed.class);
        criteria.add(Restrictions.eq("recordId", recordId));
        List<Speed> edges = (List<Speed>) criteria.list();

        return edges;
    }

    public List<Speed> selectAllByEdgeId(String edgeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Speed.class);
        criteria.add(Restrictions.eq("edgeId", edgeId));
        List<Speed> edges = (List<Speed>) criteria.list();

        return edges;
    }

    public List<Speed> selectAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Speed.class);
        List<Speed> edges = (List<Speed>) criteria.list();
        return edges;
    }
}

