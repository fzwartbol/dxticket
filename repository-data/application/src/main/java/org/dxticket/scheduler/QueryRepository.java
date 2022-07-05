package org.dxticket.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

public class QueryRepository {
    private static Logger log = LoggerFactory.getLogger(QueryRepository.class);

    private final Session session;

    public QueryRepository(Session session) {
        this.session = session;
    }

    public Boolean hasMatchEventID(String eventId) throws RepositoryException {
        String SQL = "SELECT * FROM [dxticket:eventsdocument] where [dxticket:EventId] = '"
                + eventId + "'";
       Query q = session.getWorkspace().getQueryManager().createQuery(SQL, Query.JCR_SQL2);
       QueryResult r = q.execute();
       return (r.getNodes().getSize()!= 0);
    }

    public NodeIterator getNodeResults(String eventId) {
        NodeIterator nodes = null;
        try {
            String SQL = "SELECT * FROM [dxticket:eventsdocument] where [dxticket:EventId] = '"
                    + eventId + "'";
            Query q = session.getWorkspace().getQueryManager().createQuery(SQL, Query.JCR_SQL2);
            QueryResult r = q.execute();
            nodes = r.getNodes();
        } catch (RepositoryException e) {
            log.error(e.getMessage());
        }
        //TODO null check
        return nodes;
    }

}
