package com.impltech.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.impltech.service.serialize.CompanyUserDeserializer;
import com.impltech.service.serialize.SeasonDeserializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Pinch.
 */
@Entity
@Table(name = "pinch")
public class Pinch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "total_stems")
    private Long totalStems;

    @Column(name = "notify_start_date")
    private LocalDate notifyStartDate;

    @JsonDeserialize(using = SeasonDeserializer.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "pinch_season",
        joinColumns = @JoinColumn(name="pinch_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "season_id", referencedColumnName = "id"))
    private Set<Season> seasons = new HashSet<>();

    @JsonDeserialize(using = CompanyUserDeserializer.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "pinch_company_user",
        joinColumns = @JoinColumn(name="pinch_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "company_user_id", referencedColumnName = "id"))
    private Set<CompanyUser> companyUsers = new HashSet<>();

    @OneToMany(mappedBy = "pinch", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<PinchVariety> pinchVarieties = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Pinch startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Pinch endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getTotalStems() {
        return totalStems;
    }

    public Pinch totalStems(Long totalStems) {
        this.totalStems = totalStems;
        return this;
    }

    public void setTotalStems(Long totalStems) {
        this.totalStems = totalStems;
    }

    public LocalDate getNotifyStartDate() {
        return notifyStartDate;
    }

    public Pinch notifyStartDate(LocalDate notifyStartDate) {
        this.notifyStartDate = notifyStartDate;
        return this;
    }

    public void setNotifyStartDate(LocalDate notifyStartDate) {
        this.notifyStartDate = notifyStartDate;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public Pinch seasons(Set<Season> seasons) {
        this.seasons = seasons;
        return this;
    }

    public Pinch addSeasons(Season season) {
        this.seasons.add(season);
        season.getPinches().add(this);
        return this;
    }

    public Pinch removeSeasons(Season season) {
        this.seasons.remove(season);
        season.getPinches().remove(this);
        return this;
    }

    public void setPinches(Set<Season> seasons) {
        this.seasons = seasons;
    }

    public Set<CompanyUser> getCompanyUsers() {
        return companyUsers;
    }

    public Pinch companyUsers(Set<CompanyUser> companyUsers) {
        this.companyUsers = companyUsers;
        return this;
    }

    public Pinch addCompanyUsers(CompanyUser companyUser) {
        this.companyUsers.add(companyUser);
        companyUser.getPinches().add(this);
        return this;
    }

    public Pinch removeCompanyUsers(CompanyUser companyUser) {
        this.companyUsers.remove(companyUser);
        companyUser.getPinches().remove(this);
        return this;
    }

    public void setCompanyUsers(Set<CompanyUser> companyUsers) {
        this.companyUsers = companyUsers;
    }


    public Set<PinchVariety> getPinchVarieties() {
        return pinchVarieties;
    }

    public Pinch pinchVarieties(Set<PinchVariety> pinchVarieties) {
        this.pinchVarieties = pinchVarieties;
        return this;
    }

    public Pinch addPinchVariety(PinchVariety pinchVariety) {
        this.pinchVarieties.add(pinchVariety);
        pinchVariety.setPinch(this);
        return this;
    }

    public Pinch removePinchVariety(PinchVariety pinchVariety) {
        this.pinchVarieties.remove(pinchVariety);
        pinchVariety.setPinch(null);
        return this;
    }

    public void setPinchVarieties(Set<PinchVariety> pinchVarieties) {
        this.pinchVarieties = pinchVarieties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pinch pinch = (Pinch) o;
        if (pinch.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pinch.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pinch{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", totalStems='" + getTotalStems() + "'" +
            ", notifyStartDate='" + getNotifyStartDate() + "'" +
            "}";
    }
}
