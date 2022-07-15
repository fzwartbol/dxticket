package org.example.components;

import com.google.common.base.Strings;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.configuration.sitemap.HstSiteMap;
import org.hippoecm.hst.configuration.sitemap.HstSiteMapItem;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.container.ContainerConstants;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.linking.HstLinkCreator;
import org.hippoecm.hst.core.request.ComponentConfiguration;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.onehippo.cms7.essentials.components.CommonComponent.PAGE_404;
import static org.onehippo.cms7.essentials.components.CommonComponent.PAGE_NOT_FOUND;

public abstract class BaseRestComponent extends BaseHstComponent {
    private static Logger log = LoggerFactory.getLogger(CustomListComponent.class);
    public RestTemplate restTemplate;

    @Override
    public void init(ServletContext servletContext, ComponentConfiguration componentConfig) throws HstComponentException {
        super.init(servletContext, componentConfig);
        this.restTemplate = new RestTemplate();
    }

    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        super.doBeforeRender(request, response);
        String path = request.getRequestContext().getBaseURL().getPathInfo();
        if(getRestObject(path) != null) {
            request.setModel(getComponentName(), getRestObject(path));
        } else {
            pageNotFound(response);
        }
    }

    public Object getRestObject(String path)  {
        Object jsonResponseObject=null;
        try {
            jsonResponseObject = retrieveObjectByID(getIdFromPathInfo(path));
        } catch (Throwable throwable) {
            log.info(throwable.getMessage());
        }
       return jsonResponseObject;
    }

    public abstract String getComponentName ();

    public abstract String getIdFromPathInfo(String path);

    public abstract Object retrieveObjectByID (String id);

    public void pageNotFound(HstResponse response) {
        final HstRequestContext context = RequestContextProvider.get();
        if (Boolean.TRUE.equals(context.getAttribute(ContainerConstants.FORWARD_RECURSION_ERROR))) {
            log.warn("Skip pageNotFound since recursion detected. Only set 404 status and proceed page rendering");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String pageNotFoundPath = getComponentParameter(PAGE_404);
        if (Strings.isNullOrEmpty(pageNotFoundPath)) {
            pageNotFoundPath = PAGE_404;
        }

        final HippoBean bean = context.getSiteContentBaseBean().getBean(pageNotFoundPath, HippoBean.class);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        if (bean != null) {
            final HstLinkCreator hstLinkCreator = context.getHstLinkCreator();
            final HstLink hstLink = hstLinkCreator.create(bean.getNode(), context);
            try {
                response.sendRedirect(hstLink.toUrlForm(context, false));
            } catch (IOException e) {
                log.warn("Error redirecting to 404 page: [{}]", PAGE_404);
            }
        } else {
            // check if we have pagenotfound config
            final ResolvedSiteMapItem resolvedSiteMapItem = RequestContextProvider.get().getResolvedSiteMapItem();
            if (resolvedSiteMapItem == null) {
                return;
            }
            final HstSiteMap siteMap = resolvedSiteMapItem.getHstSiteMapItem().getHstSiteMap();
            final HstSiteMapItem pagenotfound = siteMap.getSiteMapItemByRefId(PAGE_NOT_FOUND);
            if (pagenotfound != null) {
                String link = pagenotfound.getValue();
                try {
                    response.forward('/' + link);
                } catch (IOException e) {
                    log.error("Error forwarding to "+ PAGE_NOT_FOUND +" page", e);
                }
            }
        }
    }


}
