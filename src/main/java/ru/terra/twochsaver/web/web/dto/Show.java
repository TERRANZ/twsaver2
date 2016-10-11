package ru.terra.twochsaver.web.web.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by terranz on 21.01.16.
 */
@XmlRootElement
public class Show {
    public String url;
    public String preparedUrl;

    public Show() {
    }

    public Show(String url, String preparedUrl) {
        this.url = url;
        this.preparedUrl = preparedUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreparedUrl() {
        return preparedUrl;
    }

    public void setPreparedUrl(String preparedUrl) {
        this.preparedUrl = preparedUrl;
    }
}
