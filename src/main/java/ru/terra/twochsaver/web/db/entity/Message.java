package ru.terra.twochsaver.web.db.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @author terranz
 */
@Entity
@XmlRootElement
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date added;
    @Column(length = 128)
    private String image1;
    @Column(length = 128)
    private String image2;
    @Column(length = 128)
    private String image3;
    @Column(length = 128)
    private String image4;
    @Column(length = 32)
    private String md51;
    @Column(length = 32)
    private String md52;
    @Column(length = 32)
    private String md53;
    @Column(length = 32)
    private String md54;
    @Lob
    @Column(length = 2147483647)
    private String msg;
    @Lob
    @Column(length = 2147483647)
    private String subject;
    @JoinColumn(name = "thread", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Thr thread;

    public Message() {
    }

    public Message(Integer id) {
        this.id = id;
    }

    public Message(Integer id, Date added) {
        this.id = id;
        this.added = added;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getMd51() {
        return md51;
    }

    public void setMd51(String md51) {
        this.md51 = md51;
    }

    public String getMd52() {
        return md52;
    }

    public void setMd52(String md52) {
        this.md52 = md52;
    }

    public String getMd53() {
        return md53;
    }

    public void setMd53(String md53) {
        this.md53 = md53;
    }

    public String getMd54() {
        return md54;
    }

    public void setMd54(String md54) {
        this.md54 = md54;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Thr getThread() {
        return thread;
    }

    public void setThread(Thr thread) {
        this.thread = thread;
    }
}
