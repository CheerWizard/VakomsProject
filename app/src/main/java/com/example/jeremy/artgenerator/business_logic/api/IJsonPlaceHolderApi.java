package com.example.jeremy.artgenerator.business_logic.api;

import com.example.jeremy.artgenerator.business_logic.data.Album;
import com.example.jeremy.artgenerator.business_logic.data.Comment;
import com.example.jeremy.artgenerator.business_logic.data.Image;
import com.example.jeremy.artgenerator.business_logic.data.Post;
import com.example.jeremy.artgenerator.business_logic.data.Todo;
import com.example.jeremy.artgenerator.business_logic.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IJsonPlaceHolderApi {
    interface Users {
        @PUT("users/{id}")
        Call<User> putUser(@Path("id") int id, @Body User user);

        @PATCH("users/{id}")
        Call<User> patchUser(@Path("id") int id, @Body User user);

        @GET("users")
        Call<User> getUser(@Query("email") String email);

        @GET("users")
        Call<User> getUser(@Query("id") int id);

        @POST("users")
        Call<User> postUser(@Body User user);

        @DELETE("users/{email}")
        Call<User> deleteUser(@Path("email") String email);

        @DELETE("users/{id}")
        Call<User> deleteUser(@Path("id") int id);

        @GET("users")
        Call<List<User>> getAllUsers();

        @DELETE("users")
        Call<List<User>> deleteUsers();
    }
    interface Albums {
        @PUT("albums/{id}")
        Call<Album> putAlbum(@Path("id") int id, @Body Album album);

        @PATCH("albums/{id}")
        Call<Album> patchAlbum(@Path("id") int id, @Body Album album);

        @GET("albums")
        Call<List<Album>> getAllAlbums();

        @GET("albums")
        Call<List<Album>> getAlbums(@Query("userId") int userId);

        @POST("albums")
        Call<Album> postAlbum(@Body Album album);

        @DELETE("albums/{userId}")
        Call<List<Album>> deleteAlbums(@Path("userId") int userId);

        @DELETE("albums/{id}")
        Call<Album> deleteAlbum(@Path("id") int id);

        @DELETE("albums")
        Call<List<Album>> deleteAllAlbums();
    }
    interface Comments {
        @PUT("comments/{id}")
        Call<Comment> putComment(@Path("id") int id, @Body Comment comment);

        @PATCH("comments/{id}")
        Call<Comment> patchComment(@Path("id") int id, @Body Comment comment);

        @GET("comments")
        Call<List<Comment>> getAllComments();

        @GET("comments")
        Call<List<Comment>> getComments(@Query("userId") int userId);

        @POST("comments")
        Call<Comment> postComment(@Body Comment comment);

        @DELETE("comments/{postId}")
        Call<List<Comment>> deleteComments(@Path("postId") int postId);

        @DELETE("comments/{id}")
        Call<Comment> deleteComment(@Path("id") int id);

        @DELETE("comments")
        Call<List<Comment>> deleteAllComments();
    }
    interface Images {
        @PUT("photos/{id}")
        Call<Image> putImage(@Path("id") int id, @Body Image image);

        @PATCH("photos/{id}")
        Call<Image> patchImage(@Path("id") int id, @Body Image image);

        @GET("photos")
        Call<List<Image>> getAllImages();

        @GET("photos")
        Call<List<Image>> getImages(@Query("albumId") int albumId);

        @POST("photos")
        Call<Image> postImage(@Body Image image);

        @DELETE("photos/{albumId}")
        Call<List<Image>> deleteImages(@Path("albumId") int albumId);

        @DELETE("photos/{id}")
        Call<Image> deleteImage(@Path("id") int id);

        @DELETE("photos")
        Call<List<Image>> deleteAllImages();
    }
    interface Posts {
        @PUT("posts/{id}")
        Call<Post> putPost(@Path("id") int id, @Body Post post);

        @PATCH("posts/{id}")
        Call<Post> patchPost(@Path("id") int id, @Body Post post);

        @GET("posts")
        Call<List<Post>> getAllPosts();

        @GET("posts")
        Call<List<Post>> getPosts(@Query("userId") int userId);

        @POST("posts")
        Call<Post> postPost(@Body Post post);

        @DELETE("posts/{userId}")
        Call<List<Post>> deletePosts(@Path("userId") int userId);

        @DELETE("posts/{id}")
        Call<Post> deletePost(@Path("id") int id);

        @DELETE("posts")
        Call<List<Post>> deleteAllPosts();
    }
    interface Todos {
        @PUT("todos/{id}")
        Call<Todo> putTodo(@Path("id") int id, @Body Todo todo);

        @PATCH("todos/{id}")
        Call<Todo> patchTodo(@Path("id") int id, @Body Todo todo);

        @GET("todos")
        Call<List<Todo>> getAllTodos();

        @GET("todos")
        Call<List<Todo>> getTodos(@Query("userId") int userId);

        @POST("todos")
        Call<Todo> postTodo(@Body Todo todo);

        @DELETE("todos/{userId}")
        Call<List<Todo>> deleteTodos(@Path("userId") int userId);

        @DELETE("todos/{id}")
        Call<Todo> deleteTodo(@Path("id") int id);

        @DELETE("todos")
        Call<List<Todo>> deleteAllTodos();
    }
}