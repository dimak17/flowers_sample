package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Position.
 */
@Entity
@Table(name = "position")
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Company company;

    @ManyToMany(mappedBy = "positions")
    @JsonIgnore
    private Set<CompanyUser> companyUsers = new HashSet<>();

    @ManyToMany(mappedBy = "positions")
    @JsonIgnore
    private Set<Season> seasons = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private Authority authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Position name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public Position company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<CompanyUser> getCompanyUsers() {
        return companyUsers;
    }

    public Position companyUsers(Set<CompanyUser> companyUsers) {
        this.companyUsers = companyUsers;
        return this;
    }

    public Position addCompanyUsers(CompanyUser companyUser) {
        this.companyUsers.add(companyUser);
        companyUser.getPositions().add(this);
        return this;
    }

    public Position removeCompanyUsers(CompanyUser companyUser) {
        this.companyUsers.remove(companyUser);
        companyUser.getPositions().remove(this);
        return this;
    }

    public void setCompanyUsers(Set<CompanyUser> companyUsers) {
        this.companyUsers = companyUsers;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public Position seasons(Set<Season> seasons) {
        this.seasons = seasons;
        return this;
    }

    public Position addSeasons(Season season) {
        this.seasons.add(season);
        season.getPositions().add(this);
        return this;
    }

    public Position removeSeasons(Season season) {
        this.seasons.remove(season);
        season.getPositions().remove(this);
        return this;
    }

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }

    public Authority getAuthority() {
        return authority;
    }

    public Position authority(Authority authority) {
        this.authority = authority;
        return this;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        if (position.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), position.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Position{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
