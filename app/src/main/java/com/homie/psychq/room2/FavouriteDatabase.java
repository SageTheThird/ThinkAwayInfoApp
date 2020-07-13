package com.homie.psychq.room2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {PostFavEntity.class, ArticleFavEntity.class, PostReadEntity.class},version = 3)
public abstract class FavouriteDatabase extends RoomDatabase {

    public static final String DATABASE_NAME="FavouritesDB";

    public abstract PostFavDAO getPostFavDAO();
    public abstract ArticleFavDAO getArticleFavDAO();
    public abstract PostReadDAO getPostReadDAO();

    private static volatile FavouriteDatabase INSTANCE;


    public static FavouriteDatabase getInstance(Context context, RoomDatabase.Callback callback) {
        if (INSTANCE == null) {
            synchronized (FavouriteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavouriteDatabase.class, DATABASE_NAME).addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static FavouriteDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (FavouriteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavouriteDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
