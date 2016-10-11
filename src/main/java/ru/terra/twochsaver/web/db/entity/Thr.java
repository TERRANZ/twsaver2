package ru.terra.twochsaver.web.db.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author terranz
 */
@Entity
@XmlRootElement
public class Thr implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String url;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date added;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
    private Integer count;
    private Integer checked;
    private Integer finished;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thrId", fetch = FetchType.LAZY)
    private List<Img> imgList;
    private String board;

    public Thr() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    @XmlTransient
    @JsonIgnore
    public List<Img> getImgList() {
        return imgList;
    }

    public void setImgList(List<Img> imgList) {
        this.imgList = imgList;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }
}
