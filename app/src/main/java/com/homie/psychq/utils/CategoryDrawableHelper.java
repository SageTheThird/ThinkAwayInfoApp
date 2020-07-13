package com.homie.psychq.utils;





import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;


/*Class is responsible for fetching vector/drawable related to each category
* drawable is used in FeedsPostModel in Circular ImageView*/
public class CategoryDrawableHelper {


    public CategoryDrawableHelper() {
    }


    public static int getDrawableForCategory(String category){


        if (BaseApplication.get().getString(R.string.Quotes).equals(category)) {
            return R.drawable.quotes;
        } else if (BaseApplication.get().getString(R.string.PsychologicalTricks).equals(category)) {
            return R.drawable.psychological_tricks;
        } else if (BaseApplication.get().getString(R.string.PsychologicalDisorders).equals(category)) {
            return R.drawable.psychological_disorder;
        } else if (BaseApplication.get().getString(R.string.Psychology).equals(category)) {
            return R.drawable.psychology;
        } else if (BaseApplication.get().getString(R.string.Phobias).equals(category)) {
            return R.drawable.phobias;
        } else if (BaseApplication.get().getString(R.string.Philosophy).equals(category)) {
            return R.drawable.philosophy;
        } else if (BaseApplication.get().getString(R.string.Pessimism).equals(category)) {
            return R.drawable.pessimism;
        } else if (BaseApplication.get().getString(R.string.PersonalityTypes).equals(category)) {
            return R.drawable.personality_types;
        } else if (BaseApplication.get().getString(R.string.Movies).equals(category)) {
            return R.drawable.movies;
        } else if (BaseApplication.get().getString(R.string.MoviesFacts).equals(category)) {
            return R.drawable.movies_facts;
        } else if (BaseApplication.get().getString(R.string.Literature).equals(category)) {
            return R.drawable.literature;
        } else if (BaseApplication.get().getString(R.string.LifePointers).equals(category)) {
            return R.drawable.life_pointers;
        } else if (BaseApplication.get().getString(R.string.InterestingWords).equals(category)) {
            return R.drawable.interesting_words;
        } else if (BaseApplication.get().getString(R.string.Idioms).equals(category)) {
            return R.drawable.idioms;
        } else if (BaseApplication.get().getString(R.string.HumanBehavior).equals(category)) {
            return R.drawable.human_behavior;
        } else if (BaseApplication.get().getString(R.string.Grammar).equals(category)) {
            return R.drawable.grammar;
        } else if (BaseApplication.get().getString(R.string.Facts).equals(category)) {
            return R.drawable.facts;
        } else if (BaseApplication.get().getString(R.string.Emotions).equals(category)) {
            return R.drawable.emotions;
        } else if (BaseApplication.get().getString(R.string.CriticalThinking).equals(category)) {
            return R.drawable.critical_thinking;
        } else if (BaseApplication.get().getString(R.string.ColorPsychology).equals(category)) {
            return R.drawable.color_psychology;
        } else if (BaseApplication.get().getString(R.string.BooksReferences).equals(category)) {
            return R.drawable.books_references;
        }
        return R.drawable.ic_forward;
    }
}
