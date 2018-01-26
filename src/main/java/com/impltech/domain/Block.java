package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Block.
 */
@Entity
@Table(name = "block")
public class Block implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "beds")
    private Integer beds;

    @ManyToMany
    @JoinTable(name = "block_variety",
               joinColumns = @JoinColumn(name="blocks_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="varieties_id", referencedColumnName="id"))
    private Set<Variety> varieties = new HashSet<>();

    @ManyToOne
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Block name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBeds() {
        return beds;
    }

    public Block beds(Integer beds) {
        this.beds = beds;
        return this;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Set<Variety> getVarieties() {
        return varieties;
    }

    public Block varieties(Set<Variety> varieties) {
        this.varieties = varieties;
        return this;
    }

    public void setVarieties(Set<Variety> varieties) {
        this.varieties = varieties;
    }

    public Company getCompany() {
        return company;
    }

    public Block company(Company company) {
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
        Block block = (Block) o;
        if (block.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), block.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Block{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", beds='" + getBeds() + "'" +
            "}";
    }
}
