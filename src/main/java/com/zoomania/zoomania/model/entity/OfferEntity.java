package com.zoomania.zoomania.model.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offers")
public class OfferEntity extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @Basic
    private String breed;
    @Column(nullable = false)
    private LocalDateTime createdOn;
    @ManyToOne(optional = false)
    private CategoryEntity category;
    @ManyToOne(optional = false,cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity = UserEntity.class)
    private UserEntity seller;
    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = ImageEntity.class,
            mappedBy = "offer")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ImageEntity> imagesEntities = new ArrayList<>();

    public void addImage(ImageEntity imageEntity) {
        this.imagesEntities.add(imageEntity);
    }

    public void removeAllImages() {
       this.imagesEntities.clear();
    }
    public List<ImageEntity> getImagesEntities() {
        return imagesEntities;
    }

    public OfferEntity setImagesEntities(List<ImageEntity> imagesEntities) {
        this.imagesEntities = imagesEntities;
        return this;
    }

    public String getBreed() {
        return breed;
    }

    public OfferEntity setBreed(String breed) {
        this.breed = breed;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public OfferEntity setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }
    public CategoryEntity getCategory() {
        return category;
    }

    public OfferEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public UserEntity getSeller() {
        return seller;
    }

    public OfferEntity setSeller(UserEntity seller) {
        this.seller = seller;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public OfferEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
