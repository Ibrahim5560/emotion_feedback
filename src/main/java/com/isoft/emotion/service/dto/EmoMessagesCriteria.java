package com.isoft.emotion.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.isoft.emotion.domain.EmoMessages} entity. This class is used
 * in {@link com.isoft.emotion.web.rest.EmoMessagesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /emo-messages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmoMessagesCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter counter;

    private LongFilter trsId;

    private LongFilter userId;

    private StringFilter message;

    private IntegerFilter status;

    private StringFilter applicantName;

    private LongFilter emoCenterId;

    private LongFilter emoSystemId;

    private LongFilter emoSystemServicesId;

    private LongFilter emoUsersId;

    public EmoMessagesCriteria() {}

    public EmoMessagesCriteria(EmoMessagesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.counter = other.counter == null ? null : other.counter.copy();
        this.trsId = other.trsId == null ? null : other.trsId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.applicantName = other.applicantName == null ? null : other.applicantName.copy();
        this.emoCenterId = other.emoCenterId == null ? null : other.emoCenterId.copy();
        this.emoSystemId = other.emoSystemId == null ? null : other.emoSystemId.copy();
        this.emoSystemServicesId = other.emoSystemServicesId == null ? null : other.emoSystemServicesId.copy();
        this.emoUsersId = other.emoUsersId == null ? null : other.emoUsersId.copy();
    }

    @Override
    public EmoMessagesCriteria copy() {
        return new EmoMessagesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCounter() {
        return counter;
    }

    public void setCounter(LongFilter counter) {
        this.counter = counter;
    }

    public LongFilter getTrsId() {
        return trsId;
    }

    public void setTrsId(LongFilter trsId) {
        this.trsId = trsId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public StringFilter getMessage() {
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public StringFilter getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(StringFilter applicantName) {
        this.applicantName = applicantName;
    }

    public LongFilter getEmoCenterId() {
        return emoCenterId;
    }

    public void setEmoCenterId(LongFilter emoCenterId) {
        this.emoCenterId = emoCenterId;
    }

    public LongFilter getEmoSystemId() {
        return emoSystemId;
    }

    public void setEmoSystemId(LongFilter emoSystemId) {
        this.emoSystemId = emoSystemId;
    }

    public LongFilter getEmoSystemServicesId() {
        return emoSystemServicesId;
    }

    public void setEmoSystemServicesId(LongFilter emoSystemServicesId) {
        this.emoSystemServicesId = emoSystemServicesId;
    }

    public LongFilter getEmoUsersId() {
        return emoUsersId;
    }

    public void setEmoUsersId(LongFilter emoUsersId) {
        this.emoUsersId = emoUsersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmoMessagesCriteria that = (EmoMessagesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(counter, that.counter) &&
            Objects.equals(trsId, that.trsId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(message, that.message) &&
            Objects.equals(status, that.status) &&
            Objects.equals(applicantName, that.applicantName) &&
            Objects.equals(emoCenterId, that.emoCenterId) &&
            Objects.equals(emoSystemId, that.emoSystemId) &&
            Objects.equals(emoSystemServicesId, that.emoSystemServicesId) &&
            Objects.equals(emoUsersId, that.emoUsersId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            counter,
            trsId,
            userId,
            message,
            status,
            applicantName,
            emoCenterId,
            emoSystemId,
            emoSystemServicesId,
            emoUsersId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmoMessagesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (counter != null ? "counter=" + counter + ", " : "") +
                (trsId != null ? "trsId=" + trsId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (applicantName != null ? "applicantName=" + applicantName + ", " : "") +
                (emoCenterId != null ? "emoCenterId=" + emoCenterId + ", " : "") +
                (emoSystemId != null ? "emoSystemId=" + emoSystemId + ", " : "") +
                (emoSystemServicesId != null ? "emoSystemServicesId=" + emoSystemServicesId + ", " : "") +
                (emoUsersId != null ? "emoUsersId=" + emoUsersId + ", " : "") +
            "}";
    }
}
