package training.standardfeatures.answer._03;
/*
 * Copyright 2020 Bloomreach B.V. (http://www.bloomreach.com)
 * Usage is prohibited except for people attending a training given or authorised by Bloomreach B.V., and only for that purpose.
 */

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsBannerComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsBannerComponentInfo;

@ParametersInfo(type = EssentialsBannerComponentInfo.class)
public class BannerComponent extends EssentialsBannerComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        EssentialsBannerComponentInfo paramInfo = this.getComponentParametersInfo(request);
        String documentPath = paramInfo.getDocument();
        HippoBean root = request.getRequestContext().getSiteContentBaseBean();
        //TODO 1: Get the Banner bean
        //Banner banner = root.getBean(documentPath)
        //TODO 2: Create a new BannerModel object
        //BannerModel bannerModel = new BannerModel(banner);
        //TODO 3: Set the bannermodel object in the request
        //request.setModel("myData", bannerModel);

    }
}