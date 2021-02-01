package com.isoft.emotion.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the {@link com.isoft.emotion.domain.EmoSystemServices} entity.
 */
@ApiModel(description = "EmoSystemServices (emo_system_services) entity.\n@author Ibrahim Hassanin.")
public class EmoSystemServicesDTO implements Serializable {
    private Long id;

    private String nameAr;

    private String nameEn;

    private String code;

    private Integer status;

    private Long emoSystemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getEmoSystemId() {
        return emoSystemId;
    }

    public void setEmoSystemId(Long emoSystemId) {
        this.emoSystemId = emoSystemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmoSystemServicesDTO)) {
            return false;
        }

        return id != null && id.equals(((EmoSystemServicesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmoSystemServicesDTO{" +
            "id=" + getId() +
            ", nameAr='" + getNameAr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", code='" + getCode() + "'" +
            ", status=" + getStatus() +
            ", emoSystemId=" + getEmoSystemId() +
            "}";
    }
}
