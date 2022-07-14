package org.example.components;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.ComponentConfiguration;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContext;
import java.util.regex.Pattern;

import static org.example.components.CustomListComponent.USERS_API_BASEPATH;

public class RESTComponent extends BaseHstComponent {
    private RestTemplate restTemplate;

    @Override
    public void init(ServletContext servletContext, ComponentConfiguration componentConfig) throws HstComponentException {
        super.init(servletContext, componentConfig);
        this.restTemplate = new RestTemplate();
    }

    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        super.doBeforeRender(request, response);
        String path = request.getRequestContext().getBaseURL().getPathInfo();
        request.setModel("User",retrieveObjectByID(getIdFromPathInfo(path)));
    }

    public String getIdFromPathInfo(String path) {
       return path.replaceFirst(Pattern.quote("/"+"users"+"/"), "").replaceAll(".html","");
    }

    public Object retrieveObjectByID (String id) {
        return restTemplate.getForObject(USERS_API_BASEPATH+"/"+id,Object.class);
    }


}
