package org.dxticket.documents.documentmodifier;

import org.dxticket.documents.documenttypes.NodeDocumentType;
import org.hippoecm.repository.api.Document;
import org.hippoecm.repository.api.HippoWorkspace;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.api.WorkflowManager;
import org.hippoecm.repository.standardworkflow.FolderWorkflow;
import org.onehippo.repository.documentworkflow.DocumentWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.rmi.RemoteException;

public abstract class DocumentModifier<Model> {
    protected static Logger log = LoggerFactory.getLogger(DocumentModifier.class);
    protected final Session session;
    protected final NodeDocumentType<Model> nodeDocumentType;

    public DocumentModifier(Session session, NodeDocumentType<Model> nodeDocumentType) {
        this.nodeDocumentType = nodeDocumentType;
        this.session = session;
        log.info("DocumentModifier of type {} created",this.getClass());
    }

    public Node createDocument(Node destinationFolderNode, String documentType, String documentName) throws RepositoryException, WorkflowException, RemoteException {
        WorkflowManager workflowManager = ((HippoWorkspace) session.getWorkspace()).getWorkflowManager();
        FolderWorkflow folderWorkflow = (FolderWorkflow) workflowManager.getWorkflow("embedded", destinationFolderNode);
        String documentVariantPath = folderWorkflow.add("new-document", documentType,documentName);
        log.info("New document created @ {}",documentVariantPath);
        return session.getNode(documentVariantPath);
    }

    public void updateDocument(Node node, Model model){
        try {
            WorkflowManager workflowManager = ((HippoWorkspace) session.getWorkspace()).getWorkflowManager();
            DocumentWorkflow documentWorkflow = (DocumentWorkflow) workflowManager.getWorkflow("default", node.getParent());
            try {
                documentWorkflow.depublish();
            }catch (Throwable t) {}
            Document document = documentWorkflow.obtainEditableInstance();
            Node editablevariant = document.getNode(session);
            setProperties(editablevariant,model);
            session.save();
            documentWorkflow.commitEditableInstance();
            documentWorkflow.publish();
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
    }


    public abstract void setProperties(Node node, Model model) throws RepositoryException;

    public void newDocument(Node rootnode, Model model) {
        try {
            Node documentNode =createDocument(rootnode,nodeDocumentType.getDocumentType(),
                    nodeDocumentType.getJavaModelClass().getClass().getSimpleName().toLowerCase()
                            +"-"+System.currentTimeMillis());
            updateDocument(documentNode,model);
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
    }
}
