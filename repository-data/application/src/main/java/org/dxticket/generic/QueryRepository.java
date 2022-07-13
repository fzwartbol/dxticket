package org.dxticket.generic;

import org.dxticket.documents.documenttypes.NodeDocumentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import java.util.Optional;

public class QueryRepository {
    private static Logger log = LoggerFactory.getLogger(QueryRepository.class);

    private final Session session;
    private final NodeDocumentType documentType;

    public QueryRepository(NodeDocumentType documentType,Session session ) {
        this.session = session;
        this.documentType = documentType;
        log.info("Query for document type {} created",NodeDocumentType.class);
    }

    public Optional<NodeIterator> getNodesById(String id){

        NodeIterator nodes = null;
        try {
            String SQL = "SELECT * FROM ["
                    +documentType.getDocumentType()
                    +"] where ["
                    +documentType.getDocumentIdentifier()
                    +"] = '"
                    + id + "'";
            log.info("Query {}",SQL);
            Query q = session.getWorkspace().getQueryManager().createQuery(SQL, Query.JCR_SQL2);

            QueryResult r = q.execute();
            nodes = r.getNodes();
        } catch (RepositoryException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(nodes);
    }
}
