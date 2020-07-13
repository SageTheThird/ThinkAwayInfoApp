package com.homie.psychq.intro;



/*Model for intro pages*/
public class IntroModel {


    private int vector;
    private String title;
    private String description;


    public IntroModel(int vector, String title, String description) {
        this.vector = vector;
        this.title = title;
        this.description = description;
    }

    public IntroModel() {
    }


    @Override
    public String toString() {
        return "IntroModel{" +
                "vector=" + vector +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getVector() {
        return vector;
    }

    public void setVector(int vector) {
        this.vector = vector;
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
}
