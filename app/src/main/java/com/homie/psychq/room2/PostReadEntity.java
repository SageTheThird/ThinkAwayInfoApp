package com.homie.psychq.room2;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/*Posts read table in database*/


@Entity(tableName = "PostRead")
public class PostReadEntity implements Parcelable {


    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "postId")
    private String post_id;


    public PostReadEntity(int id, String post_id) {
        this.id = id;
        this.post_id = post_id;
    }

    public PostReadEntity() {
    }

    protected PostReadEntity(Parcel in) {
        id = in.readInt();
        post_id = in.readString();
    }

    public static final Creator<PostReadEntity> CREATOR = new Creator<PostReadEntity>() {
        @Override
        public PostReadEntity createFromParcel(Parcel in) {
            return new PostReadEntity(in);
        }

        @Override
        public PostReadEntity[] newArray(int size) {
            return new PostReadEntity[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(post_id);
    }
}