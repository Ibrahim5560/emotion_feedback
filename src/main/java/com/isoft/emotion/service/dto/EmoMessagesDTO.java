package com.isoft.emotion.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the {@link com.isoft.emotion.domain.EmoMessages} entity.
 */
@ApiModel(description = "EmoMessages (emo_messages) entity.\n@author Ibrahim Hassanin.")
public class EmoMessagesDTO implements Serializable {
    private Long id;

    private Long counter;

    private Long trsId;

    private Long userId;

    private String message;

    private Integer status;

    private String applicantName;

    private Long emoCenterId;

    private Long emoSystemId;

    private Long emoSystemServicesId;

    private Long emoUsersId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Long getEmoCenterId() {
        return emoCenterId;
    }

    public void setEmoCenterId(Long emoCenterId) {
        this.emoCenterId = emoCenterId;
    }

    public Long getEmoSystemId() {
        return emoSystemId;
    }

    public void setEmoSystemId(Long emoSystemId) {
        this.emoSystemId = emoSystemId;
    }

    public Long getEmoSystemServicesId() {
        return emoSystemServicesId;
    }

    public void setEmoSystemServicesId(Long emoSystemServicesId) {
        this.emoSystemServicesId = emoSystemServicesId;
    }

    public Long getEmoUsersId() {
        return emoUsersId;
    }

    public void setEmoUsersId(Long emoUsersId) {
        this.emoUsersId = emoUsersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmoMessagesDTO)) {
            return false;
        }

        return id != null && id.equals(((EmoMessagesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmoMessagesDTO{" +
            "id=" + getId() +
            ", counter=" + getCounter() +
            ", trsId=" + getTrsId() +
            ", userId=" + getUserId() +
            ", message='" + getMessage() + "'" +
            ", status=" + getStatus() +
            ", applicantName='" + getApplicantName() + "'" +
            ", emoCenterId=" + getEmoCenterId() +
            ", emoSystemId=" + getEmoSystemId() +
            ", emoSystemServicesId=" + getEmoSystemServicesId() +
            ", emoUsersId=" + getEmoUsersId() +
            "}";
    }
}
