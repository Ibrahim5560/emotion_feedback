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
 * Criteria class for the {@link com.isoft.emotion.domain.EmoSystem} entity. This class is used
 * in {@link com.isoft.emotion.web.rest.EmoSystemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /emo-systems?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmoSystemCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nameAr;

    private StringFilter nameEn;

    private StringFilter code;

    private IntegerFilter status;

    private LongFilter emoSystemMessagesId;

    private LongFilter emoSystemServicesId;

    public EmoSystemCriteria() {}

    public EmoSystemCriteria(EmoSystemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nameAr = other.nameAr == null ? null : other.nameAr.copy();
        this.nameEn = other.nameEn == null ? null : other.nameEn.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.emoSystemMessagesId = other.emoSystemMessagesId == null ? null : other.emoSystemMessagesId.copy();
        this.emoSystemServicesId = other.emoSystemServicesId == null ? null : other.emoSystemServicesId.copy();
    }

    @Override
    public EmoSystemCriteria copy() {
        return new EmoSystemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNameAr() {
        return nameAr;
    }

    public void setNameAr(StringFilter nameAr) {
        this.nameAr = nameAr;
    }

    public StringFilter getNameEn() {
        return nameEn;
    }

    public void setNameEn(StringFilter nameEn) {
        this.nameEn = nameEn;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public LongFilter getEmoSystemMessagesId() {
        return emoSystemMessagesId;
    }

    public void setEmoSystemMessagesId(LongFilter emoSystemMessagesId) {
        this.emoSystemMessagesId = emoSystemMessagesId;
    }

    public LongFilter getEmoSystemServicesId() {
        return emoSystemServicesId;
    }

    public void setEmoSystemServicesId(LongFilter emoSystemServicesId) {
        this.emoSystemServicesId = emoSystemServicesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmoSystemCriteria that = (EmoSystemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nameAr, that.nameAr) &&
            Objects.equals(nameEn, that.nameEn) &&
            Objects.equals(code, that.code) &&
            Objects.equals(status, that.status) &&
            Objects.equals(emoSystemMessagesId, that.emoSystemMessagesId) &&
            Objects.equals(emoSystemServicesId, that.emoSystemServicesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameAr, nameEn, code, status, emoSystemMessagesId, emoSystemServicesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmoSystemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nameAr != null ? "nameAr=" + nameAr + ", " : "") +
                (nameEn != null ? "nameEn=" + nameEn + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (emoSystemMessagesId != null ? "emoSystemMessagesId=" + emoSystemMessagesId + ", " : "") +
                (emoSystemServicesId != null ? "emoSystemServicesId=" + emoSystemServicesId + ", " : "") +
            "}";
    }
}
