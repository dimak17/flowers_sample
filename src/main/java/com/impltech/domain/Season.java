package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.impltech.service.serialize.MarketSeasonDeserializer;
import com.impltech.service.serialize.MarketSeasonSerializer;
import com.impltech.service.serialize.PositionDeserializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Season.
 */
@Entity
@Table(name = "season")
public class Season implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "season_name")
    private String seasonName;

    @Column(name = "season_year")
    private Integer seasonYear;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "start_date")
    private LocalDate startDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "end_date")
    private LocalDate endDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "notify_start_date")
    private LocalDate notifyStartDate;

    @ManyToMany(mappedBy = "seasons")
    @JsonIgnore
    private Set<Pinch> pinches = new HashSet<>();

    @JsonSerialize(using = MarketSeasonSerializer.class)
    @JsonDeserialize(using = MarketSeasonDeserializer.class)
    @OneToMany(mappedBy = "season", orphanRemoval = true, cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<MarketSeason> marketSeasons = new HashSet<>();

    @JsonDeserialize(using = PositionDeserializer.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "season_position",
        joinColumns = @JoinColumn(name="season_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "position_id", referencedColumnName = "id"))
    private Set<Position> positions = new HashSet<>();

    @ManyToOne
    private Company company;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public Season seasonName(String seasonName) {
        this.seasonName = seasonName;
        return this;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public Integer getSeasonYear() {
        return seasonYear;
    }

    public Season seasonYear(Integer seasonYear) {
        this.seasonYear = seasonYear;
        return this;
    }

    public void setSeasonYear(Integer seasonYear) {
        this.seasonYear = seasonYear;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Season startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Season endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getNotifyStartDate() {
        return notifyStartDate;
    }

    public Season notifyStartDate(LocalDate notifyStartDate) {
        this.notifyStartDate = notifyStartDate;
        return this;
    }

    public void setNotifyStartDate(LocalDate notifyStartDate) {
        this.notifyStartDate = notifyStartDate;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public Season positions(Set<Position> positions) {
        this.positions = positions;
        return this;
    }

    public Season addPosition(Position position) {
        this.positions.add(position);
        position.getSeasons().add(this);
        return this;
    }

    public Season removePosition(Position position) {
        this.positions.remove(position);
        position.getSeasons().remove(this);
        return this;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Set<Pinch> getPinches() {
        return pinches;
    }

    public Season pinches(Set<Pinch> pinches) {
        this.pinches = pinches;
        return this;
    }

    public Season addPinches(Pinch pinch) {
        this.pinches.add(pinch);
        pinch.getSeasons().add(this);
        return this;
    }

    public Season removePinches(Pinch pinch) {
        this.pinches.remove(pinch);
        pinch.getSeasons().remove(this);
        return this;
    }

    public void setPinches(Set<Pinch> pinches) {
        this.pinches = pinches;
    }

    public Set<MarketSeason> getMarketSeasons() {
        return marketSeasons;
    }

    public Season marketSeasons(Set<MarketSeason> marketSeasons) {
        this.marketSeasons = marketSeasons;
        return this;
    }

    public Season addMarketSeason(MarketSeason marketSeason) {
        this.marketSeasons.add(marketSeason);
        marketSeason.setSeason(this);
        return this;
    }

    public Season removeMarketSeason(MarketSeason marketSeason) {
        this.marketSeasons.remove(marketSeason);
        marketSeason.setSeason(null);
        return this;
    }

    public void setMarketSeasons(Set<MarketSeason> marketSeasons) {
        this.marketSeasons = marketSeasons;
    }

    public Company getCompany() {
        return company;
    }

    public Season company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Season season = (Season) o;
        if (season.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), season.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Season{" +
            "id=" + getId() +
            ", seasonName='" + getSeasonName() + "'" +
            ", seasonYear='" + getSeasonYear() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", notifyStartDate='" + getNotifyStartDate() + "'" +
            ", company=" + getCompany() +
            "}";
    }
}
