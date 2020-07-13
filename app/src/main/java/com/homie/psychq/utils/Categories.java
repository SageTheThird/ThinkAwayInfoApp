package com.homie.psychq.utils;

import com.homie.psychq.main.models.categories.CategoryUnitPsych;

import java.util.ArrayList;
import java.util.List;


/*Categories in the app so far*/

public class Categories {

    private static List<String> tags_list=new ArrayList<>();
    private static List<CategoryUnitPsych> categories_list=new ArrayList<>();



    public static final String CRITICAL_THINKING="Critical Thinking";
    public static final String QUOTES="Quotes";
    public static final String BOOK_REFERENCES="Book References";
    public static final String COLOR_PSYCHOLOGY="Color Psychology";
    public static final String LITERATURE="Literature";
    public static final String GRAMMAR="Grammar";
    public static final String BEHAVIOUR_PSYCHOLOGY="Behaviour Psychology";
    public static final String PHOBIAS="Phobias";
    public static final String INTERESTING_WORDS="Interesting Words";
    public static final String PERSONALITY_TYPES="Personality Types";
    public static final String EMOTIONS="Emotions";
    public static final String COMPELLING_FACTS="Compelling Facts";


    public static List<String> getTags_list() {
        tags_list.add(CRITICAL_THINKING);
        tags_list.add(QUOTES);
        tags_list.add(BOOK_REFERENCES);
        tags_list.add(COLOR_PSYCHOLOGY);
        tags_list.add(LITERATURE);
        tags_list.add(GRAMMAR);
        tags_list.add(BEHAVIOUR_PSYCHOLOGY);
        tags_list.add(PHOBIAS);
        tags_list.add(INTERESTING_WORDS);
        tags_list.add(PERSONALITY_TYPES);
        tags_list.add(EMOTIONS);
        tags_list.add(COMPELLING_FACTS);

        return tags_list;
    }


}
