package com.isoft.emotion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * EmoSystemServices (emo_system_services) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "emo_system_services")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmoSystemServices extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sys_srv_seq")
    @SequenceGenerator(name = "sys_srv_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "emoSystemServices")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmoMessages> emoSystemServicesMessages = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "emoSystemServices", allowSetters = true)
    private EmoSystem emoSystem;

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

    public EmoSystemServices nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public EmoSystemServices nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public EmoSystemServices code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public EmoSystemServices status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<EmoMessages> getEmoSystemServicesMessages() {
        return emoSystemServicesMessages;
    }

    public EmoSystemServices emoSystemServicesMessages(Set<EmoMessages> emoMessages) {
        this.emoSystemServicesMessages = emoMessages;
        return this;
    }

    public EmoSystemServices addEmoSystemServicesMessages(EmoMessages emoMessages) {
        this.emoSystemServicesMessages.add(emoMessages);
        emoMessages.setEmoSystemServices(this);
        return this;
    }

    public EmoSystemServices removeEmoSystemServicesMessages(EmoMessages emoMessages) {
        this.emoSystemServicesMessages.remove(emoMessages);
        emoMessages.setEmoSystemServices(null);
        return this;
    }

    public void setEmoSystemServicesMessages(Set<EmoMessages> emoMessages) {
        this.emoSystemServicesMessages = emoMessages;
    }

    public EmoSystem getEmoSystem() {
        return emoSystem;
    }

    public EmoSystemServices emoSystem(EmoSystem emoSystem) {
        this.emoSystem = emoSystem;
        return this;
    }

    public void setEmoSystem(EmoSystem emoSystem) {
        this.emoSystem = emoSystem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmoSystemServices)) {
            return false;
        }
        return id != null && id.equals(((EmoSystemServices) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmoSystemServices{" +
            "id=" + getId() +
            ", nameAr='" + getNameAr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", code='" + getCode() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
