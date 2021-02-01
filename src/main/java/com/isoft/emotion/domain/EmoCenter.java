package com.isoft.emotion.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * EmoCenter (emo_center) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "emo_center")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmoCenter extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "center_seq")
    @SequenceGenerator(name = "center_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "emoCenter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmoMessages> emoCenterMessages = new HashSet<>();

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

    public EmoCenter nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public EmoCenter nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public EmoCenter code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public EmoCenter status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<EmoMessages> getEmoCenterMessages() {
        return emoCenterMessages;
    }

    public EmoCenter emoCenterMessages(Set<EmoMessages> emoMessages) {
        this.emoCenterMessages = emoMessages;
        return this;
    }

    public EmoCenter addEmoCenterMessages(EmoMessages emoMessages) {
        this.emoCenterMessages.add(emoMessages);
        emoMessages.setEmoCenter(this);
        return this;
    }

    public EmoCenter removeEmoCenterMessages(EmoMessages emoMessages) {
        this.emoCenterMessages.remove(emoMessages);
        emoMessages.setEmoCenter(null);
        return this;
    }

    public void setEmoCenterMessages(Set<EmoMessages> emoMessages) {
        this.emoCenterMessages = emoMessages;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmoCenter)) {
            return false;
        }
        return id != null && id.equals(((EmoCenter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmoCenter{" +
            "id=" + getId() +
            ", nameAr='" + getNameAr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", code='" + getCode() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
