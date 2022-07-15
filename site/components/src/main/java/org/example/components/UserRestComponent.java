package org.example.components;

import java.util.regex.Pattern;

import static org.example.components.CustomListComponent.USERS_API_BASEPATH;

public class UserRestComponent extends BaseRestComponent{
    final static String USER_COMPONENT_NAME="User";


    @Override
    public String getComponentName() {
        return USER_COMPONENT_NAME;
    }

    public String getIdFromPathInfo(String path) {
        return path.replaceFirst(Pattern.quote("/"+"users"+"/"), "").replaceAll(".html","");
    }

    public Object retrieveObjectByID (String id) {
        return restTemplate.getForObject(USERS_API_BASEPATH+"/"+id,Object.class);
    }
}
