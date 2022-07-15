package org.dxticket.scheduler;


import org.dxticket.DTO.DTO;
import org.dxticket.configuration.Credentials;
import org.dxticket.configuration.MatcherConfiguration;
import org.dxticket.consumer.ApiConsumer;
import org.dxticket.documents.documentmodifier.DocumentModifier;

import org.dxticket.generic.QueryRepository;
import org.onehippo.repository.scheduling.RepositoryJob;
import org.onehippo.repository.scheduling.RepositoryJobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.util.NoSuchElementException;

public class ImporterJob implements RepositoryJob {
    private static final Logger log = LoggerFactory.getLogger(ImporterJob.class);
    private MatcherConfiguration configProperties;
    private ApiConsumer apiConsumer;

    public ImporterJob(MatcherConfiguration configProperties){
        this.configProperties = configProperties;
        this.apiConsumer = new ApiConsumer(configProperties.getApiEndpoint(), configProperties.getAPIModelDTO());
    }
    @Override
    public void execute(RepositoryJobExecutionContext repositoryJobExecutionContext)  {
        Session session = null;
        log.info("Successfully started scheduled job for import");
        try  {
            session = repositoryJobExecutionContext.createSession(new SimpleCredentials(Credentials.USERNAME, Credentials.PASSWORD.toCharArray()));
            QueryRepository queryRepository = new QueryRepository(configProperties.getDocumentType(), session);
            DocumentModifier documentModifier = configProperties.getDocumentModifier(session);
            Node rootContentNode = getRootNode(session, configProperties.getContentDocumentBasepath());
            apiConsumer.getList().forEach(dto -> matchDTO(dto, rootContentNode,queryRepository, documentModifier));
            session.save();
        } catch (Throwable e) {
            log.error(e.getMessage());
        } finally {
            if(session!=null) {
                session.logout();
            }
        }
    }

    public void matchDTO(DTO dto, Node rootNode, QueryRepository queryRepository, DocumentModifier documentModifier) {
            log.info("Matching object {} with node",dto);
            NodeIterator nodelist = queryRepository.getNodesById(dto.getId()).orElseThrow((()->new NoSuchElementException()));
            if(nodelist.getSize()==0) {
                log.info("No match found, creating new document for node");
                documentModifier.newDocument(rootNode,dto.getModel());
            } else {
                log.info("Match found, updating node creating new document for node");
                nodelist.forEachRemaining( node -> documentModifier.updateDocument((Node) node,dto.getModel()));
            }
        }

    private Node getRootNode(Session session, String path) throws RepositoryException {
        final Node node = session.getRootNode().getNode(path);
        return node;
    }
}
