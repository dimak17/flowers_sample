package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Variety.
 */
@Entity
@Table(name = "variety")
public class Variety implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "breeder")
    private String breeder;

    @Column(name = "min_length")
    private Integer minLength;

    @Column(name = "max_length")
    private Integer maxLength;

    @ManyToMany
    @JoinTable(name = "variety_block",
               joinColumns = @JoinColumn(name="varieties_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="blocks_id", referencedColumnName="id"))
    private Set<Block> blocks = new HashSet<>();

    @ManyToOne
    private Company company;

    @ManyToOne
    private TypeOfFlower typeOfFlower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Variety name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public Variety color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBreeder() {
        return breeder;
    }

    public Variety breeder(String breeder) {
        this.breeder = breeder;
        return this;
    }

    public void setBreeder(String breeder) {
        this.breeder = breeder;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public Variety minLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public Variety maxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Set<Block> getBlocks() {
        return blocks;
    }

    public Variety blocks(Set<Block> blocks) {
        this.blocks = blocks;
        return this;
    }

    public Variety addBlock(Block block) {
        this.blocks.add(block);
        block.getVarieties().add(this);
        return this;
    }

    public Variety removeBlock(Block block) {
        this.blocks.remove(block);
        block.getVarieties().remove(this);
        return this;
    }

    public void setBlocks(Set<Block> blocks) {
        this.blocks = blocks;
    }

    public Company getCompany() {
        return company;
    }

    public Variety company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public TypeOfFlower getTypeOfFlower() {
        return typeOfFlower;
    }

    public Variety typeOfFlower(TypeOfFlower typeOfFlower) {
        this.typeOfFlower = typeOfFlower;
        return this;
    }

    public void setTypeOfFlower(TypeOfFlower typeOfFlower) {
        this.typeOfFlower = typeOfFlower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Variety variety = (Variety) o;
        if (variety.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), variety.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Variety{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            ", breeder='" + getBreeder() + "'" +
            ", minLength='" + getMinLength() + "'" +
            ", maxLength='" + getMaxLength() + "'" +
            "}";
    }
}
