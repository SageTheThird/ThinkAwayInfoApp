package com.homie.psychq.main.models.categories;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryFeatured implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("sub_categories")
    @Expose
    private String sub_categoriesd;

    @SerializedName("pic_top")
    @Expose
    private String pic_top;

    @SerializedName("pic_left")
    @Expose
    private String pic_left;

    @SerializedName("pic_right")
    @Expose
    private String pic_right;

    @SerializedName("count_range_from")
    @Expose
    private String count_range_from;

    @SerializedName("count_range_to")
    @Expose
    private String count_range_to;

    @SerializedName("tags")
    @Expose
    private String tags;

    @SerializedName("total_posts_count")
    @Expose
    private String total_posts_count;


    public CategoryFeatured() {
    }

    public CategoryFeatured(String id, String title, String description, String sub_categoriesd, String pic_top, String pic_left, String pic_right, String count_range_from, String count_range_to, String tags, String total_posts_count) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sub_categoriesd = sub_categoriesd;
        this.pic_top = pic_top;
        this.pic_left = pic_left;
        this.pic_right = pic_right;
        this.count_range_from = count_range_from;
        this.count_range_to = count_range_to;
        this.tags = tags;
        this.total_posts_count = total_posts_count;
    }

    protected CategoryFeatured(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        sub_categoriesd = in.readString();
        pic_top = in.readString();
        pic_left = in.readString();
        pic_right = in.readString();
        count_range_from = in.readString();
        count_range_to = in.readString();
        tags = in.readString();
        total_posts_count = in.readString();
    }

    public static final Creator<CategoryFeatured> CREATOR = new Creator<CategoryFeatured>() {
        @Override
        public CategoryFeatured createFromParcel(Parcel in) {
            return new CategoryFeatured(in);
        }

        @Override
        public CategoryFeatured[] newArray(int size) {
            return new CategoryFeatured[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSub_categoriesd() {
        return sub_categoriesd;
    }

    public void setSub_categoriesd(String sub_categoriesd) {
        this.sub_categoriesd = sub_categoriesd;
    }

    public String getPic_top() {
        return pic_top;
    }

    public void setPic_top(String pic_top) {
        this.pic_top = pic_top;
    }

    public String getPic_left() {
        return pic_left;
    }

    public void setPic_left(String pic_left) {
        this.pic_left = pic_left;
    }

    public String getPic_right() {
        return pic_right;
    }

    public void setPic_right(String pic_right) {
        this.pic_right = pic_right;
    }

    public String getCount_range_from() {
        return count_range_from;
    }

    public void setCount_range_from(String count_range_from) {
        this.count_range_from = count_range_from;
    }

    public String getCount_range_to() {
        return count_range_to;
    }

    public void setCount_range_to(String count_range_to) {
        this.count_range_to = count_range_to;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTotal_posts_count() {
        return total_posts_count;
    }

    public void setTotal_posts_count(String total_posts_count) {
        this.total_posts_count = total_posts_count;
    }

    @Override
    public String toString() {
        return "CategoryFeatured{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sub_categoriesd='" + sub_categoriesd + '\'' +
                ", pic_top='" + pic_top + '\'' +
                ", pic_left='" + pic_left + '\'' +
                ", pic_right='" + pic_right + '\'' +
                ", count_range_from='" + count_range_from + '\'' +
                ", count_range_to='" + count_range_to + '\'' +
                ", tags='" + tags + '\'' +
                ", total_posts_count='" + total_posts_count + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(sub_categoriesd);
        parcel.writeString(pic_top);
        parcel.writeString(pic_left);
        parcel.writeString(pic_right);
        parcel.writeString(count_range_from);
        parcel.writeString(count_range_to);
        parcel.writeString(tags);
        parcel.writeString(total_posts_count);
    }
}
