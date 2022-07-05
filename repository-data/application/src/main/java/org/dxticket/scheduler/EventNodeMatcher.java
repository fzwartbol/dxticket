package org.dxticket.scheduler;

import org.dxticket.configuration.Credentials;
import org.dxticket.consumer.EventsConsumer;
import org.dxticket.model.EventDTO;
import org.hippoecm.repository.HippoRepository;
import org.hippoecm.repository.HippoRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.List;

import static org.dxticket.configuration.DocumentPaths.CONTENT_DOCUMENTS_EVENT_BASEPATH;


public class EventNodeMatcher {
    private static final Logger log = LoggerFactory.getLogger(EventNodeMatcher.class);
    private EventsConsumer eventsConsumer;

    public EventNodeMatcher() {
        this.eventsConsumer = new EventsConsumer();
    }

    public void matchEvents() {
        Session session = null;
        List<EventDTO> eventList = eventsConsumer.getEventList();
        try  {
            session = getLoggedInSession();
            QueryRepository queryRepository = new QueryRepository(session);
            EventDocumentModifier eventDocumentModifier = new EventDocumentModifier(session);
            Node rootNode = getRootNode(session, CONTENT_DOCUMENTS_EVENT_BASEPATH);

            eventList.forEach(e -> {
                NodeIterator nodelist = queryRepository.getNodeResults(e.getEvent().getId());
                    if(nodelist.getSize()==0) {
                        log.info("No match found, creating new document for node");
                        eventDocumentModifier.createDocument(rootNode, e.getEvent());
                    } else {
                            nodelist.forEachRemaining( node -> eventDocumentModifier.updateDocument((Node) node,e.getEvent()));
                    }
                });
            session.save();
        } catch (Throwable e) {
            log.debug(e.getMessage());
        } finally {
            if(session!=null) {
                session.logout();
            }
        }
    }

    public Session getLoggedInSession() throws RepositoryException {
        HippoRepository repository = HippoRepositoryFactory.getHippoRepository("vm://");
        return repository.login(Credentials.USERNAME, Credentials.PASSWORD.toCharArray());
    }

    public Node getRootNode(Session session, String path) throws RepositoryException {
        final Node node = session.getRootNode().getNode(path);
        return node;
    }

}
