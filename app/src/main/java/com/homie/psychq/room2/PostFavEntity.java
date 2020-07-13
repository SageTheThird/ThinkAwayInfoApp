package com.homie.psychq.room2;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/*Bookmarked posts table in database*/

@Entity(tableName = "PostFav")
public class PostFavEntity implements Parcelable {


    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "postId")
    private String post_id;


    @ColumnInfo(name = "title")
    private String title;


    @ColumnInfo(name = "description")
    private String description;


    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "subCategory")
    private String subCategory;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;


    public PostFavEntity() {
    }

    public PostFavEntity(int id, String post_id, String title, String description, String category, String subCategory, String thumbnail) {
        this.id = id;
        this.post_id = post_id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.thumbnail = thumbnail;
    }

    protected PostFavEntity(Parcel in) {
        id = in.readInt();
        post_id = in.readString();
        title = in.readString();
        description = in.readString();
        category = in.readString();
        subCategory = in.readString();
        thumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(post_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(subCategory);
        dest.writeString(thumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PostFavEntity> CREATOR = new Creator<PostFavEntity>() {
        @Override
        public PostFavEntity createFromParcel(Parcel in) {
            return new PostFavEntity(in);
        }

        @Override
        public PostFavEntity[] newArray(int size) {
            return new PostFavEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
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

    @Override
    public String toString() {
        return "PostFavEntity{" +
                "id=" + id +
                ", post_id='" + post_id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}