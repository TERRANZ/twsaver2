package ru.terra.twochsaver.web.db.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author terranz
 */
@Entity
@XmlRootElement
public class Img implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false, length = 150)
    private String url;
    @JoinColumn(name = "thr_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Thr thrId;
    @Basic(optional = false)
    @Column(nullable = false, length = 35)
    private String md5hash;

    public Img() {
    }

    public Img(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Thr getThrId() {
        return thrId;
    }

    public void setThrId(Thr thrId) {
        this.thrId = thrId;
    }

    public String getMd5hash() {
        return md5hash;
    }

    public void setMd5hash(String md5hash) {
        this.md5hash = md5hash;
    }
}
