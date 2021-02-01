package com.isoft.emotion.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * EmoMessageFeedback (emo_message_feedback) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "emo_message_feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmoMessageFeedback extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "msg_fbck_seq")
    @SequenceGenerator(name = "msg_fbck_seq", allocationSize = 1)
    private Long id;

    @Column(name = "emo_system_id")
    private Long emoSystemId;

    @Column(name = "center_id")
    private Long centerId;

    @Column(name = "emo_system_services_id")
    private Long emoSystemServicesId;

    @Column(name = "counter")
    private Long counter;

    @Column(name = "trs_id")
    private Long trsId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    private Integer status;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "applicant_name")
    private String applicantName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmoSystemId() {
        return emoSystemId;
    }

    public EmoMessageFeedback emoSystemId(Long emoSystemId) {
        this.emoSystemId = emoSystemId;
        return this;
    }

    public void setEmoSystemId(Long emoSystemId) {
        this.emoSystemId = emoSystemId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public EmoMessageFeedback centerId(Long centerId) {
        this.centerId = centerId;
        return this;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public Long getEmoSystemServicesId() {
        return emoSystemServicesId;
    }

    public EmoMessageFeedback emoSystemServicesId(Long emoSystemServicesId) {
        this.emoSystemServicesId = emoSystemServicesId;
        return this;
    }

    public void setEmoSystemServicesId(Long emoSystemServicesId) {
        this.emoSystemServicesId = emoSystemServicesId;
    }

    public Long getCounter() {
        return counter;
    }

    public EmoMessageFeedback counter(Long counter) {
        this.counter = counter;
        return this;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getTrsId() {
        return trsId;
    }

    public EmoMessageFeedback trsId(Long trsId) {
        this.trsId = trsId;
        return this;
    }

    public void setTrsId(Long trsId) {
        this.trsId = trsId;
    }

    public Long getUserId() {
        return userId;
    }

    public EmoMessageFeedback userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public EmoMessageFeedback message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public EmoMessageFeedback status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public EmoMessageFeedback feedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public EmoMessageFeedback applicantName(String applicantName) {
        this.applicantName = applicantName;
        return this;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmoMessageFeedback)) {
            return false;
        }
        return id != null && id.equals(((EmoMessageFeedback) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmoMessageFeedback{" +
            "id=" + getId() +
            ", emoSystemId=" + getEmoSystemId() +
            ", centerId=" + getCenterId() +
            ", emoSystemServicesId=" + getEmoSystemServicesId() +
            ", counter=" + getCounter() +
            ", trsId=" + getTrsId() +
            ", userId=" + getUserId() +
            ", message='" + getMessage() + "'" +
            ", status=" + getStatus() +
            ", feedback='" + getFeedback() + "'" +
            ", applicantName='" + getApplicantName() + "'" +
            "}";
    }
}
