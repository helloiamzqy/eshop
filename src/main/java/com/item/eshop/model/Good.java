package com.item.eshop.model;

import java.math.BigDecimal;

public class Good {
    private Integer id;

    private String name;

    private String introduce;

    private String image;

    private String content;

    private Integer spanSize;

    private Integer likes;

    private BigDecimal price;

    private Integer counts;

    private Integer category;

    private Integer status;

    private String other;

    private Integer sale_volume;

    public Integer getSale_volume() {
        return sale_volume;
    }

    public void setSale_volume(Integer sale_volume) {
        this.sale_volume = sale_volume;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(Integer spanSize) {
        this.spanSize = spanSize;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
}