package org.example.components;
/*
 * Copyright 2020 Bloomreach B.V. (http://www.bloomreach.com)
 * Usage is prohibited except for people attending a training given or authorised by Bloomreach B.V., and only for that purpose.
 */
import org.example.beans.Banner;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.rewriter.impl.SimpleContentRewriter;
import org.hippoecm.hst.core.request.HstRequestContext;

public class BannerModel {

    private String title;
    private String imageLink;
    private String content;

    public BannerModel(final Banner banner) {
        HstRequestContext hstRequestContext = RequestContextProvider.get();
        this.setTitle(banner.getTitle());
        this.setImageLink(hstRequestContext.getHstLinkCreator().create(banner.getImage(), hstRequestContext).toUrlForm(hstRequestContext, true));
        SimpleContentRewriter contentRewriter = new SimpleContentRewriter();
        this.setContent(contentRewriter.rewrite(banner.getContent().getContent(), hstRequestContext));
    }

    public String getTitle() {
        return title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
