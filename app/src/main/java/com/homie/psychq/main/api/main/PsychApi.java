package com.homie.psychq.main.api.main;



import com.homie.psychq.main.models.crashcourses.Article;
import com.homie.psychq.main.models.categories.CategoryUnitPsych;
import com.homie.psychq.main.models.crashcourses.CrashCourseUnit;
import com.homie.psychq.main.models.feeds.PsychPhoto;
import com.homie.psychq.main.models.feeds.Results;
import com.homie.psychq.main.models.feeds.ResultsAnnouncements;
import com.homie.psychq.main.models.crashcourses.ResultsArticles;
import com.homie.psychq.main.models.categories.ResultsCategories;
import com.homie.psychq.main.models.crashcourses.ResultsCrashCourses;
import com.homie.psychq.main.models.feeds.Subscription;
import java.util.List;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*All the main and important end-points of the app data*/
public interface PsychApi {


        /*
        * POSTS END-POINTS
        * */
        @GET("api/psychq/posts/")
        Flowable<Results> getAllPosts(@Query("page") int pageNumber);

        @GET("api/psychq/posts/{id}")
        Flowable<PsychPhoto> getPostById(@Path("id") String id);

        /*For searching with tags use '_' with query*/
        @GET("api/psychq/posts/") // ?q={id/category/title}
        Flowable<Results> getPostsByCategory (@Query("q") String category_name, @Query("page") int pageNumber);


        @GET("api/psychq/posts/") // ?q={id/category/title}
        Flowable<Results> getPostsByCustomOrdering (@Query("ordering") String ordering_field,@Query("page") int pageNumber);

        @GET("api/psychq/posts/") // ?q={id/category/title}
        Flowable<Results> getPostsByCustomOrderingAndNotSubscribed (@Query("ordering") String ordering_field,@Query("q") String query,@Query("page") int pageNumber);

        @GET("api/psychq/posts/") // ?q={id/category/title}
        Flowable<Results> getPostsByTags (@Query("q") String queryWith_,@Query("page") int pageNumber);

        @POST("api/psychq/posts/")
        Flowable<PsychPhoto> addPost(@Body PsychPhoto updatedDAta);


        @PATCH("api/psychq/posts/{id}")
        Flowable<PsychPhoto> updatePost(@Body PsychPhoto updatedDAta, @Path("id") String  id);







        /*
        * CATEGORIES END-POINTS
        * */
        @GET("api/psychq/categories/")
        Flowable<ResultsCategories> getAllCategories(@Query("page") int pageNumber);

        @GET("api/psychq/products/{id}")
        Flowable<CategoryUnitPsych> getCategoryById(@Path("id") String id);


        @GET("api/psychq/products/") // ?q={id/product_name}
        Flowable<CategoryUnitPsych> getCategoryByNameOrId (@Query("q") String product_name);





        /**
         * ARTICLES END-POINTS
         * */
       @GET("api/psychq/articles/")
        Flowable<ResultsArticles> getAllArticles(@Query("page") int pageNumber);

        @GET("api/psychq/articles/{id}")
        Flowable<Article> getArticleById(@Path("id") String id);

        /*For searching with tags use '_' with query*/

        //below will give us articles specific to course and ordered by course_count
        @GET("api/psychq/articles/") // ?q={id/category/title}
        Flowable<ResultsArticles> getArticleByCrashCourseAndOrdering (@Query("q") String crashCourse_name, @Query("ordering") String ordering_field, @Query("page") int pageNumber);


        @GET("api/psychq/articles/") // ?q={id/category/title}
        Flowable<ResultsArticles> getArticlesByOrdering (@Query("ordering") String ordering_field,@Query("page") int pageNumber);

        @GET("api/psychq/articles/") // ?q={id/category/title}
        Flowable<ResultsArticles> getArticlesByTags (@Query("q") String queryWith_atTheStart,@Query("page") int pageNumber);

        @POST("api/psychq/articles/")
        Flowable<Article> addArticle(@Body Article updatedDAta);


        @PATCH("api/psychq/articles/{id}")
        Flowable<Article> updateArticle(@Body Article updatedDAta, @Path("id") String  id);




        /*
        * CRASH COURSES END-POINTS
        * */

        @GET("api/psychq/crashcourses/")
        Flowable<ResultsCrashCourses> getAllCrashCourses(@Query("page") int pageNumber);

        @GET("api/psychq/crashcourses/{id}")
        Flowable<CrashCourseUnit> getCrashCourseById(@Path("id") String id);


        @GET("api/psychq/crashcourses/") // ?q={id/product_name}
        Flowable<CrashCourseUnit> getCrashCourseByNameOrId (@Query("q") String product_name);




        /*
        * SUBSCRIPTION HANDLING END-POINTS
        * */
        @POST("api/psychq/subscriptions/")
        Flowable<Subscription> postSubscription(@Body Subscription subscription);

        @GET("api/psychq/subscriptions/")
        Flowable<List<Subscription>> getAllSubscriptions();

        @PATCH("api/psychq/subscriptions/{id}/")
        Flowable<Subscription> updateSubscription(@Body Subscription subscriptionUpdated, @Path("id") String  subscriptionId);

        @GET("api/psychq/subscriptions/") // ?q={id/category/title}
        Flowable<List<Subscription>> getSubscriptionByPurchaseToken (@Query("q") String purchaseToken);

        @GET("api/psychq/subscriptions/") // ?q={id/category/title}
        Flowable<List<Subscription>> getSubscriptionByUserEmail (@Query("q") String user_email);

        @GET("api/psychq/subscriptions/") // ?q={id/category/title}
        Flowable<List<Subscription>> getSubscriptionByOrderId (@Query("q") String order_id);

        @GET("api/psychq/subscriptions/") // ?q={id/category/title}
        Flowable<List<Subscription>> getSubscriptionByProduct (@Query("q") String productId);

        @GET("api/psychq/subscriptions/") // ?q={id/category/title}
        Call<List<Subscription>> getSubscriptionByProduct2 (@Query("q") String productId);




        /*
        * ANNOUNCEMENT END-POINT
        * */
        @GET("api/psychq/announcements/")
        Flowable<ResultsAnnouncements> getAllAnnouncements(@Query("page") int pageNumber);
}
