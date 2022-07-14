package org.example.components;

import com.google.common.base.Strings;
import org.example.beans.EventsDocument;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.builder.HstQueryBuilder;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.filter.BaseFilter;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.ComponentConfiguration;
import org.hippoecm.repository.util.DateTools;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;
import org.onehippo.cms7.essentials.components.paging.IterablePagination;
import org.onehippo.cms7.essentials.components.paging.Pageable;
import org.onehippo.cms7.essentials.components.utils.ComponentsUtils;
import org.onehippo.cms7.essentials.components.utils.SiteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContext;
import java.util.*;

import static org.example.components.CustomListComponent.documentType.API_DOCUMENT_TYPE;
import static org.example.components.CustomListComponent.documentType.BLOOMREACH_DOCUMENT_TYPE;

@ParametersInfo(type = CustomListComponentInfo.class)
public class CustomListComponent extends EssentialsListComponent {
    private static Logger log = LoggerFactory.getLogger(CustomListComponent.class);
    public Enum selectedDocumentType;
    public RestTemplate restTemplate;
    public static String USERS_API_BASEPATH = "http://localhost:9004/users";
    public static String TICKETS_API_BASEPATH = "http://localhost:9003/tickets";

    public final static Map<String, String> documentTypesMap = new HashMap<String, String>() {{
        put("Venues","dxticket:newsdocument");
        put("Events", EventsDocument.DOCUMENT_TYPE);
        put("Users", USERS_API_BASEPATH);
        put("Tickets", TICKETS_API_BASEPATH);
    }};

    //TODO -> add to resourcebundle
    public final static Map<String, String> documentName = new HashMap<String, String>() {{
        put("Venues","Locaties");
        put("Events", "Evenementen");
    }};

    @Override
    public void init(ServletContext servletContext, ComponentConfiguration componentConfig) throws HstComponentException {
        super.init(servletContext, componentConfig);
        this.restTemplate = new RestTemplate();
    }

    public void setDocumentType (String documentType) {
        if (documentType.equals("Venues")||documentType.equals("Events")){
            selectedDocumentType=BLOOMREACH_DOCUMENT_TYPE;}
        else {
           selectedDocumentType = API_DOCUMENT_TYPE; }
    }

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        super.doBeforeRender(request, response);
        final CustomListComponentInfo paramInfo = getComponentParametersInfo(request);
        final String documentTypes = paramInfo.getDocumentTypes();
        setDocumentType(documentTypes);
        request.setModel("componentHeaderName", paramInfo.getComponentTitle());

        if(selectedDocumentType==BLOOMREACH_DOCUMENT_TYPE) {
            request.setModel("documentType","BLOOMREACH_DOCUMENT_TYPE");
            ComponentsUtils.addCurrentDateStrings(request);
            if (Strings.isNullOrEmpty(documentTypesMap.get(documentTypes))) {
                setEditMode(request);
                return;
            }

        }
        if (selectedDocumentType==API_DOCUMENT_TYPE) {
            if (documentTypes.equals("Users")) {
                request.setModel("documentType","API_USER_DOCUMENT_TYPE");
            } else {
                request.setModel("documentType","API_TICKET_DOCUMENT_TYPE");
            }
            request.setModel("pageable", pageable( Arrays.asList(consumerApi(documentTypes)),paramInfo));
        }
    }

    public Object[] consumerApi (String documentTypes) {
        return restTemplate.getForObject(documentTypesMap.get(documentTypes),Object[].class);
    }

    public Pageable pageable (List paging, CustomListComponentInfo paramInfo) {

        IterablePagination<?> pagination = new IterablePagination(paging,1,paramInfo.getPageSize());
        pagination.processAll();
        return pagination;
    }

    @Override
    protected <T extends EssentialsListComponentInfo>
    HstQuery buildQuery(final HstRequest request, final T paramInfo, final HippoBean scope) {
        final String documentTypes = documentTypesMap.get(paramInfo.getDocumentTypes());
        final String[] types = SiteUtils.parseCommaSeparatedValue(documentTypes);
        if (log.isDebugEnabled()) {
            log.debug("Searching for document types:  {}, and including subtypes: {}", documentTypes, paramInfo.getIncludeSubtypes());
        }
        HstQueryBuilder builder = HstQueryBuilder.create(scope);
        return paramInfo.getIncludeSubtypes() ? builder.ofTypes(types).build() : builder.ofPrimaryTypes(types).build();
    }

    @Override
    protected void contributeAndFilters(final List<BaseFilter> filters, final HstRequest request, final HstQuery query) {
        final CustomListComponentInfo paramInfo = getComponentParametersInfo(request);
        if (paramInfo.getHidePastEvents()) {
            final String dateField = paramInfo.getDocumentDateField();
            if (!Strings.isNullOrEmpty(dateField)) {
                try {
                    final Filter filter = query.createFilter();
                    filter.addGreaterOrEqualThan(dateField, Calendar.getInstance(), DateTools.Resolution.DAY);
                    filters.add(filter);
                } catch (FilterException e) {
                    log.error("Error while creating query filter to hide past events using date field {}", dateField, e);
                }
            }
        }
    }


    public enum documentType{
        API_DOCUMENT_TYPE,
        BLOOMREACH_DOCUMENT_TYPE
    }


}