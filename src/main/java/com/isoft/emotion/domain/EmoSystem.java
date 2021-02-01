package com.isoft.emotion.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * EmoSystem (emo_system) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "emo_system")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmoSystem extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sys_seq")
    @SequenceGenerator(name = "sys_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "emoSystem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmoMessages> emoSystemMessages = new HashSet<>();

    @OneToMany(mappedBy = "emoSystem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmoSystemServices> emoSystemServices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public EmoSystem nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public EmoSystem nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public EmoSystem code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public EmoSystem status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<EmoMessages> getEmoSystemMessages() {
        return emoSystemMessages;
    }

    public EmoSystem emoSystemMessages(Set<EmoMessages> emoMessages) {
        this.emoSystemMessages = emoMessages;
        return this;
    }

    public EmoSystem addEmoSystemMessages(EmoMessages emoMessages) {
        this.emoSystemMessages.add(emoMessages);
        emoMessages.setEmoSystem(this);
        return this;
    }

    public EmoSystem removeEmoSystemMessages(EmoMessages emoMessages) {
        this.emoSystemMessages.remove(emoMessages);
        emoMessages.setEmoSystem(null);
        return this;
    }

    public void setEmoSystemMessages(Set<EmoMessages> emoMessages) {
        this.emoSystemMessages = emoMessages;
    }

    public Set<EmoSystemServices> getEmoSystemServices() {
        return emoSystemServices;
    }

    public EmoSystem emoSystemServices(Set<EmoSystemServices> emoSystemServices) {
        this.emoSystemServices = emoSystemServices;
        return this;
    }

    public EmoSystem addEmoSystemServices(EmoSystemServices emoSystemServices) {
        this.emoSystemServices.add(emoSystemServices);
        emoSystemServices.setEmoSystem(this);
        return this;
    }

    public EmoSystem removeEmoSystemServices(EmoSystemServices emoSystemServices) {
        this.emoSystemServices.remove(emoSystemServices);
        emoSystemServices.setEmoSystem(null);
        return this;
    }

    public void setEmoSystemServices(Set<EmoSystemServices> emoSystemServices) {
        this.emoSystemServices = emoSystemServices;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmoSystem)) {
            return false;
        }
        return id != null && id.equals(((EmoSystem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmoSystem{" +
            "id=" + getId() +
            ", nameAr='" + getNameAr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", code='" + getCode() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
