package com.homie.psychq.main.models.feeds;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PsychPhoto {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("custom_ordering")
    @Expose
    private int custom_ordering;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("sub_category")
    @Expose
    private String subCategory;

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @SerializedName("full_res_image")
    @Expose
    private String fullResImage;

    @SerializedName("universal_count")
    @Expose
    private int universalCount;

    @SerializedName("category_count")
    @Expose
    private int categoryCount;

    @SerializedName("sub_post_count")
    @Expose
    private int subPostCount;

    @SerializedName("is_printable")
    @Expose
    private boolean is_printable;

    @SerializedName("tags")
    @Expose
    private String tags;

    @SerializedName("views")
    @Expose
    private int views;

    @SerializedName("downloads")
    @Expose
    private int downloads;

    @SerializedName("shares")
    @Expose
    private int shares;

    @SerializedName("timestamp")
    @Expose
    private String timeCreated;


    public PsychPhoto() {
    }

    public PsychPhoto(String id, int custom_ordering, String title, String description, String category, String subCategory, String thumbnail, String fullResImage, int universalCount, int categoryCount, int subPostCount, boolean is_printable, String tags, int views, int downloads, int shares, String timeCreated) {
        this.id = id;
        this.custom_ordering = custom_ordering;
        this.title = title;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.thumbnail = thumbnail;
        this.fullResImage = fullResImage;
        this.universalCount = universalCount;
        this.categoryCount = categoryCount;
        this.subPostCount = subPostCount;
        this.is_printable = is_printable;
        this.tags = tags;
        this.views = views;
        this.downloads = downloads;
        this.shares = shares;
        this.timeCreated = timeCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCustom_ordering() {
        return custom_ordering;
    }

    public void setCustom_ordering(int custom_ordering) {
        this.custom_ordering = custom_ordering;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFullResImage() {
        return fullResImage;
    }

    public void setFullResImage(String fullResImage) {
        this.fullResImage = fullResImage;
    }

    public int getUniversalCount() {
        return universalCount;
    }

    public void setUniversalCount(int universalCount) {
        this.universalCount = universalCount;
    }

    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }

    public int getSubPostCount() {
        return subPostCount;
    }

    public void setSubPostCount(int subPostCount) {
        this.subPostCount = subPostCount;
    }

    public boolean isIs_printable() {
        return is_printable;
    }

    public void setIs_printable(boolean is_printable) {
        this.is_printable = is_printable;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Override
    public String toString() {
        return "PsychPhoto{" +
                "id='" + id + '\'' +
                ", custom_ordering=" + custom_ordering +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", fullResImage='" + fullResImage + '\'' +
                ", universalCount=" + universalCount +
                ", categoryCount=" + categoryCount +
                ", subPostCount=" + subPostCount +
                ", is_printable=" + is_printable +
                ", tags='" + tags + '\'' +
                ", views=" + views +
                ", downloads=" + downloads +
                ", shares=" + shares +
                ", timeCreated='" + timeCreated + '\'' +
                '}';
    }
}
