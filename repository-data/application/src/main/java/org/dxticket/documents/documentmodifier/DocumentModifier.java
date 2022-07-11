package org.dxticket.documents.documentmodifier;

import org.dxticket.documents.documenttypes.NodeDocumentType;
import org.hippoecm.repository.api.Document;
import org.hippoecm.repository.api.HippoWorkspace;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.api.WorkflowManager;
import org.hippoecm.repository.standardworkflow.EditableWorkflow;
import org.hippoecm.repository.standardworkflow.FolderWorkflow;
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

        // get the document handle first
        Node documentVariantNode = session.getNode(documentVariantPath);
        Node documentHandleNode = documentVariantNode.getParent();

        // get an editable workflow from the document handle node
        EditableWorkflow documentWorkflow = (EditableWorkflow) workflowManager.getWorkflow("default", documentHandleNode);

        // obtain editable document variant (a.k.a, draft document variant)
        Document document = documentWorkflow.obtainEditableInstance();
       return document.getNode(session);
    }
    //TODO split up methods

    public abstract void updateDocument(Node node, Model model);

    public void newDocument(Node rootnode, Model model) {
        try {
           Node documentNode =createDocument(rootnode,nodeDocumentType.getDocumentType(),
                    nodeDocumentType.getJavaModelClass().getClass().getSimpleName()+System.currentTimeMillis());
           updateDocument(documentNode,model);
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
    }
}
