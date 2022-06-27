package training.standardfeatures.answer._04;
/*
 * Copyright 2020 Bloomreach B.V. (http://www.bloomreach.com)
 * Usage is prohibited except for people attending a training given or authorised by Bloomreach B.V., and only for that purpose.
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hippoecm.hst.core.container.HstComponentWindow;
import org.hippoecm.hst.pagemodelapi.v09.core.model.ComponentWindowModel;

public abstract class ComponentWindowModelMixin extends ComponentWindowModel {

    public ComponentWindowModelMixin(final HstComponentWindow window) {
        super(window);
    }

    @JsonIgnore
    @Override
    public String getComponentClass() {
        return super.getComponentClass();
    }

    @JsonSerialize(converter= ComponentNameConverter.class)
    @Override
    public String getName() {
        return super.getName();
    }
}
