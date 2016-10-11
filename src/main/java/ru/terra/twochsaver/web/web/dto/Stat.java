package ru.terra.twochsaver.web.web.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Date: 21.01.15
 * Time: 20:18
 */
@XmlRootElement
public class Stat {
    public String bt;
    public Integer count;
    public Integer remaining;
    public Integer finished;
    public Integer checked;
    public String added;
    public String updated;
    public Integer messages;

    public Stat(String bt, Integer count, Integer remaining, Integer finished, Integer checked, String added, String updated, Integer messages) {
        this.bt = bt;
        this.count = count;
        this.remaining = remaining;
        this.finished = finished;
        this.checked = checked;
        this.added = added;
        this.updated = updated;
        this.messages = messages;
    }

    public Stat() {
    }

    public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getMessages() {
        return messages;
    }

    public void setMessages(Integer messages) {
        this.messages = messages;
    }
}
