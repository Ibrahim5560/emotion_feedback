package com.isoft.emotion.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the {@link com.isoft.emotion.domain.EmoMessageFeedback} entity.
 */
@ApiModel(description = "EmoMessageFeedback (emo_message_feedback) entity.\n@author Ibrahim Hassanin.")
public class EmoMessageFeedbackDTO implements Serializable {
    private Long id;

    private Long emoSystemId;

    private Long centerId;

    private Long emoSystemServicesId;

    private Long counter;

    private Long trsId;

    private Long userId;

    private String message;

    private Integer status;

    private String feedback;

    private String applicantName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmoSystemId() {
        return emoSystemId;
    }

    public void setEmoSystemId(Long emoSystemId) {
        this.emoSystemId = emoSystemId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public Long getEmoSystemServicesId() {
        return emoSystemServicesId;
    }

    public void setEmoSystemServicesId(Long emoSystemServicesId) {
        this.emoSystemServicesId = emoSystemServicesId;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getTrsId() {
        return trsId;
    }

    public void setTrsId(Long trsId) {
        this.trsId = trsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmoMessageFeedbackDTO)) {
            return false;
        }

        return id != null && id.equals(((EmoMessageFeedbackDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmoMessageFeedbackDTO{" +
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
