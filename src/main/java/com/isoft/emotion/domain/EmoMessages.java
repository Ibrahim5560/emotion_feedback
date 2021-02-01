package com.isoft.emotion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * EmoMessages (emo_messages) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "emo_messages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmoMessages extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "msg_seq")
    @SequenceGenerator(name = "msg_seq", allocationSize = 1)
    private Long id;

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

    @Column(name = "applicant_name")
    private String applicantName;

    @ManyToOne
    @JsonIgnoreProperties(value = "emoCenterMessages", allowSetters = true)
    private EmoCenter emoCenter;

    @ManyToOne
    @JsonIgnoreProperties(value = "emoSystemMessages", allowSetters = true)
    private EmoSystem emoSystem;

    @ManyToOne
    @JsonIgnoreProperties(value = "emoSystemServicesMessages", allowSetters = true)
    private EmoSystemServices emoSystemServices;

    @ManyToOne
    @JsonIgnoreProperties(value = "emoUsersMessages", allowSetters = true)
    private EmoUsers emoUsers;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCounter() {
        return counter;
    }

    public EmoMessages counter(Long counter) {
        this.counter = counter;
        return this;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getTrsId() {
        return trsId;
    }

    public EmoMessages trsId(Long trsId) {
        this.trsId = trsId;
        return this;
    }

    public void setTrsId(Long trsId) {
        this.trsId = trsId;
    }

    public Long getUserId() {
        return userId;
    }

    public EmoMessages userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public EmoMessages message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public EmoMessages status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public EmoMessages applicantName(String applicantName) {
        this.applicantName = applicantName;
        return this;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public EmoCenter getEmoCenter() {
        return emoCenter;
    }

    public EmoMessages emoCenter(EmoCenter emoCenter) {
        this.emoCenter = emoCenter;
        return this;
    }

    public void setEmoCenter(EmoCenter emoCenter) {
        this.emoCenter = emoCenter;
    }

    public EmoSystem getEmoSystem() {
        return emoSystem;
    }

    public EmoMessages emoSystem(EmoSystem emoSystem) {
        this.emoSystem = emoSystem;
        return this;
    }

    public void setEmoSystem(EmoSystem emoSystem) {
        this.emoSystem = emoSystem;
    }

    public EmoSystemServices getEmoSystemServices() {
        return emoSystemServices;
    }

    public EmoMessages emoSystemServices(EmoSystemServices emoSystemServices) {
        this.emoSystemServices = emoSystemServices;
        return this;
    }

    public void setEmoSystemServices(EmoSystemServices emoSystemServices) {
        this.emoSystemServices = emoSystemServices;
    }

    public EmoUsers getEmoUsers() {
        return emoUsers;
    }

    public EmoMessages emoUsers(EmoUsers emoUsers) {
        this.emoUsers = emoUsers;
        return this;
    }

    public void setEmoUsers(EmoUsers emoUsers) {
        this.emoUsers = emoUsers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmoMessages)) {
            return false;
        }
        return id != null && id.equals(((EmoMessages) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmoMessages{" +
            "id=" + getId() +
            ", counter=" + getCounter() +
            ", trsId=" + getTrsId() +
            ", userId=" + getUserId() +
            ", message='" + getMessage() + "'" +
            ", status=" + getStatus() +
            ", applicantName='" + getApplicantName() + "'" +
            "}";
    }
}
