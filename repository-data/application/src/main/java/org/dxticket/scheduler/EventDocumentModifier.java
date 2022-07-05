package org.dxticket.scheduler;

import org.dxticket.model.Event;
import org.hippoecm.repository.api.Document;
import org.hippoecm.repository.api.HippoWorkspace;
import org.hippoecm.repository.api.WorkflowManager;
import org.hippoecm.repository.standardworkflow.EditableWorkflow;
import org.hippoecm.repository.standardworkflow.FolderWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class EventDocumentModifier {
    private static Logger log = LoggerFactory.getLogger(EventDocumentModifier.class);
    private final Session jcrSession;

    public EventDocumentModifier(Session session) {
        log.debug("Event initiated");
        this.jcrSession = session;
    }

    public void createDocument(Node destinationFolderNode, Event event) {
            log.info("Creating new document for event {}",event.getId());
           String newDocumentName = "event-"+event.getId();
           try {
               WorkflowManager workflowManager = ((HippoWorkspace) jcrSession.getWorkspace()).getWorkflowManager();
               FolderWorkflow folderWorkflow = (FolderWorkflow) workflowManager.getWorkflow("embedded", destinationFolderNode);
               String documentVariantPath = folderWorkflow.add("new-document", "dxticket:eventsdocument", newDocumentName);

               // get the document handle first
               Node documentVariantNode = jcrSession.getNode(documentVariantPath);
               log.debug("{}", documentVariantNode.getName());
               Node documentHandleNode = documentVariantNode.getParent();

               // get an editable workflow from the document handle node
               EditableWorkflow documentWorkflow = (EditableWorkflow) workflowManager.getWorkflow("default", documentHandleNode);

               // obtain editable document variant (a.k.a, draft document variant)
               Document document = documentWorkflow.obtainEditableInstance();
               documentVariantNode = document.getNode(jcrSession);

               log.debug(documentVariantNode.getIdentifier());

               documentVariantNode.setProperty("dxticket:EventId", event.getId());
               documentVariantNode.setProperty("dxticket:eventName", event.getName());
               //TODO set dateTime property

               // commit the draft variant
               document = documentWorkflow.commitEditableInstance();
               log.debug("Committed document at " + document.getNode(jcrSession).getPath());
               jcrSession.save();

           } catch (Throwable throwable) {
               log.debug(throwable.getMessage());
           }

    }

    public void updateDocument(Node node, Event event)  {
        try {
            node.setProperty("dxticket:EventId", event.getId());
            node.setProperty("dxticket:eventName", event.getName());
            //TODO set dateTime property

        } catch (RepositoryException e) {
            log.error(e.getMessage());
        }
    }



}
