package com.isoft.emotion.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * EmoUsers (emo_users) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "emo_users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmoUsers extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emo_user_seq")
    @SequenceGenerator(name = "emo_user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "emoUsers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmoMessages> emoUsersMessages = new HashSet<>();

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

    public EmoUsers nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public EmoUsers nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public EmoUsers code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public EmoUsers status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<EmoMessages> getEmoUsersMessages() {
        return emoUsersMessages;
    }

    public EmoUsers emoUsersMessages(Set<EmoMessages> emoMessages) {
        this.emoUsersMessages = emoMessages;
        return this;
    }

    public EmoUsers addEmoUsersMessages(EmoMessages emoMessages) {
        this.emoUsersMessages.add(emoMessages);
        emoMessages.setEmoUsers(this);
        return this;
    }

    public EmoUsers removeEmoUsersMessages(EmoMessages emoMessages) {
        this.emoUsersMessages.remove(emoMessages);
        emoMessages.setEmoUsers(null);
        return this;
    }

    public void setEmoUsersMessages(Set<EmoMessages> emoMessages) {
        this.emoUsersMessages = emoMessages;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmoUsers)) {
            return false;
        }
        return id != null && id.equals(((EmoUsers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmoUsers{" +
            "id=" + getId() +
            ", nameAr='" + getNameAr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", code='" + getCode() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
