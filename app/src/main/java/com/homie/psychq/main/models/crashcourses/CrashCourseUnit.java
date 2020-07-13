package com.homie.psychq.main.models.crashcourses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrashCourseUnit implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("title")
    @Expose
    private String title;


    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("author_name")
    @Expose
    private String author_name;

    @SerializedName("source_info")
    @Expose
    private String author_info;

    @SerializedName("author_image")
    @Expose
    private String author_image;


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


    @SerializedName("first_article_url")
    @Expose
    private String firstArticleUrl;

    @SerializedName("last_article_url")
    @Expose
    private String lastArticleUrl;

    @SerializedName("tags")
    @Expose
    private String tags;

    @SerializedName("is_finished")
    @Expose
    private boolean is_finished;



    @SerializedName("total_articles_count")
    @Expose
    private String totalArticlesCount;


    public CrashCourseUnit() {
    }



    public CrashCourseUnit(String id, String title, String description, String source, String author_name, String author_info, String author_image, String pic_top, String pic_left, String pic_right, String count_range_from, String count_range_to, String firstArticleUrl, String lastArticleUrl, String tags, boolean is_finished, String totalArticlesCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.source = source;
        this.author_name = author_name;
        this.author_info = author_info;
        this.author_image = author_image;
        this.pic_top = pic_top;
        this.pic_left = pic_left;
        this.pic_right = pic_right;
        this.count_range_from = count_range_from;
        this.count_range_to = count_range_to;
        this.firstArticleUrl = firstArticleUrl;
        this.lastArticleUrl = lastArticleUrl;
        this.tags = tags;
        this.is_finished = is_finished;
        this.totalArticlesCount = totalArticlesCount;
    }

    protected CrashCourseUnit(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        source = in.readString();
        author_name = in.readString();
        author_info = in.readString();
        author_image = in.readString();
        pic_top = in.readString();
        pic_left = in.readString();
        pic_right = in.readString();
        count_range_from = in.readString();
        count_range_to = in.readString();
        firstArticleUrl = in.readString();
        lastArticleUrl = in.readString();
        tags = in.readString();
        is_finished = in.readByte() != 0;
        totalArticlesCount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(source);
        dest.writeString(author_name);
        dest.writeString(author_info);
        dest.writeString(author_image);
        dest.writeString(pic_top);
        dest.writeString(pic_left);
        dest.writeString(pic_right);
        dest.writeString(count_range_from);
        dest.writeString(count_range_to);
        dest.writeString(firstArticleUrl);
        dest.writeString(lastArticleUrl);
        dest.writeString(tags);
        dest.writeByte((byte) (is_finished ? 1 : 0));
        dest.writeString(totalArticlesCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CrashCourseUnit> CREATOR = new Creator<CrashCourseUnit>() {
        @Override
        public CrashCourseUnit createFromParcel(Parcel in) {
            return new CrashCourseUnit(in);
        }

        @Override
        public CrashCourseUnit[] newArray(int size) {
            return new CrashCourseUnit[size];
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_info() {
        return author_info;
    }

    public void setAuthor_info(String author_info) {
        this.author_info = author_info;
    }

    public String getAuthor_image() {
        return author_image;
    }

    public void setAuthor_image(String author_image) {
        this.author_image = author_image;
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

    public String getFirstArticleUrl() {
        return firstArticleUrl;
    }

    public void setFirstArticleUrl(String firstArticleUrl) {
        this.firstArticleUrl = firstArticleUrl;
    }

    public String getLastArticleUrl() {
        return lastArticleUrl;
    }

    public void setLastArticleUrl(String lastArticleUrl) {
        this.lastArticleUrl = lastArticleUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isIs_finished() {
        return is_finished;
    }

    public void setIs_finished(boolean is_finished) {
        this.is_finished = is_finished;
    }

    public String getTotalArticlesCount() {
        return totalArticlesCount;
    }

    public void setTotalArticlesCount(String totalArticlesCount) {
        this.totalArticlesCount = totalArticlesCount;
    }

    @Override
    public String toString() {
        return "CrashCourseUnit{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", source='" + source + '\'' +
                ", author_name='" + author_name + '\'' +
                ", author_info='" + author_info + '\'' +
                ", author_image='" + author_image + '\'' +
                ", pic_top='" + pic_top + '\'' +
                ", pic_left='" + pic_left + '\'' +
                ", pic_right='" + pic_right + '\'' +
                ", count_range_from='" + count_range_from + '\'' +
                ", count_range_to='" + count_range_to + '\'' +
                ", firstArticleUrl='" + firstArticleUrl + '\'' +
                ", lastArticleUrl='" + lastArticleUrl + '\'' +
                ", tags='" + tags + '\'' +
                ", is_finished=" + is_finished +
                ", totalArticlesCount='" + totalArticlesCount + '\'' +
                '}';
    }
}
