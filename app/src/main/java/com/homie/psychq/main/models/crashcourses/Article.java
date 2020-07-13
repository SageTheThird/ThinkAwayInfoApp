package com.homie.psychq.main.models.crashcourses;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Article {


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

    @SerializedName("article_content")
    @Expose
    private String articleContent;

    @SerializedName("parent_course")
    @Expose
    private String parentCourse;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("est_time")
    @Expose
    private String est_time;


    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @SerializedName("full_res_image")
    @Expose
    private String fullResImage;

    @SerializedName("universal_count")
    @Expose
    private int universalCount;

    @SerializedName("course_count")
    @Expose
    private int courseCount;

    @SerializedName("tags")
    @Expose
    private String tags;

    @SerializedName("reads")
    @Expose
    private int reads;

    @SerializedName("downloads")
    @Expose
    private int downloads;

    @SerializedName("shares")
    @Expose
    private int shares;

    @SerializedName("timestamp")
    @Expose
    private String timeCreated;


    public Article() {
    }

    public Article(String id, int custom_ordering, String title, String description, String articleContent, String parentCourse, String source, String est_time, String thumbnail, String fullResImage, int universalCount, int courseCount, String tags, int reads, int downloads, int shares, String timeCreated) {
        this.id = id;
        this.custom_ordering = custom_ordering;
        this.title = title;
        this.description = description;
        this.articleContent = articleContent;
        this.parentCourse = parentCourse;
        this.source = source;
        this.est_time = est_time;
        this.thumbnail = thumbnail;
        this.fullResImage = fullResImage;
        this.universalCount = universalCount;
        this.courseCount = courseCount;
        this.tags = tags;
        this.reads = reads;
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

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getParentCourse() {
        return parentCourse;
    }

    public void setParentCourse(String parentCourse) {
        this.parentCourse = parentCourse;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEst_time() {
        return est_time;
    }

    public void setEst_time(String est_time) {
        this.est_time = est_time;
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

    public int getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(int courseCount) {
        this.courseCount = courseCount;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getReads() {
        return reads;
    }

    public void setReads(int reads) {
        this.reads = reads;
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
        return "Article{" +
                "id='" + id + '\'' +
                ", custom_ordering=" + custom_ordering +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", parentCourse='" + parentCourse + '\'' +
                ", source='" + source + '\'' +
                ", est_time='" + est_time + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", fullResImage='" + fullResImage + '\'' +
                ", universalCount=" + universalCount +
                ", courseCount=" + courseCount +
                ", tags='" + tags + '\'' +
                ", reads=" + reads +
                ", downloads=" + downloads +
                ", shares=" + shares +
                ", timeCreated='" + timeCreated + '\'' +
                '}';
    }
}
