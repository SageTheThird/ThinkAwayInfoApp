package com.homie.psychq.room2;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*Bookmarked articles table in database*/

@Entity(tableName = "ArticleFav")
public class ArticleFavEntity implements Parcelable {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "articleId")
    private String articleId;


    @ColumnInfo(name = "title")
    private String title;


    @ColumnInfo(name = "thumbnail")
    private String thumbnail;


    @ColumnInfo(name = "parentCourse")
    private String parentCourse;

    @ColumnInfo(name = "course_count")
    private int course_count;

    @ColumnInfo(name = "article_content")
    private String article_content;


    public ArticleFavEntity() {
    }

    public ArticleFavEntity(int id, String articleId, String title, String thumbnail, String parentCourse, int course_count, String article_content) {
        this.id = id;
        this.articleId = articleId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.parentCourse = parentCourse;
        this.course_count = course_count;
        this.article_content = article_content;
    }

    protected ArticleFavEntity(Parcel in) {
        id = in.readInt();
        articleId = in.readString();
        title = in.readString();
        thumbnail = in.readString();
        parentCourse = in.readString();
        course_count = in.readInt();
        article_content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(articleId);
        dest.writeString(title);
        dest.writeString(thumbnail);
        dest.writeString(parentCourse);
        dest.writeInt(course_count);
        dest.writeString(article_content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArticleFavEntity> CREATOR = new Creator<ArticleFavEntity>() {
        @Override
        public ArticleFavEntity createFromParcel(Parcel in) {
            return new ArticleFavEntity(in);
        }

        @Override
        public ArticleFavEntity[] newArray(int size) {
            return new ArticleFavEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getParentCourse() {
        return parentCourse;
    }

    public void setParentCourse(String parentCourse) {
        this.parentCourse = parentCourse;
    }

    public int getCourse_count() {
        return course_count;
    }

    public void setCourse_count(int course_count) {
        this.course_count = course_count;
    }

    public String getArticle_content() {
        return article_content;
    }

    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }

    @Override
    public String toString() {
        return "ArticleFavEntity{" +
                "id=" + id +
                ", articleId='" + articleId + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", parentCourse='" + parentCourse + '\'' +
                ", course_count=" + course_count +
                ", article_content='" + article_content + '\'' +
                '}';
    }
}