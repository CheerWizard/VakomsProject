package com.example.jeremy.artgenerator.business_logic.databases;

import com.example.jeremy.artgenerator.business_logic.dao.AddressDAO;
import com.example.jeremy.artgenerator.business_logic.dao.AlbumDAO;
import com.example.jeremy.artgenerator.business_logic.dao.CommentDAO;
import com.example.jeremy.artgenerator.business_logic.dao.CompanyDAO;
import com.example.jeremy.artgenerator.business_logic.dao.GeoDAO;
import com.example.jeremy.artgenerator.business_logic.dao.ImageDAO;
import com.example.jeremy.artgenerator.business_logic.dao.PostDAO;
import com.example.jeremy.artgenerator.business_logic.dao.TodoDAO;
import com.example.jeremy.artgenerator.business_logic.dao.UserDAO;
import com.example.jeremy.artgenerator.business_logic.data.Address;
import com.example.jeremy.artgenerator.business_logic.data.Album;
import com.example.jeremy.artgenerator.business_logic.data.Comment;
import com.example.jeremy.artgenerator.business_logic.data.Company;
import com.example.jeremy.artgenerator.business_logic.data.Geo;
import com.example.jeremy.artgenerator.business_logic.data.Image;
import com.example.jeremy.artgenerator.business_logic.data.Post;
import com.example.jeremy.artgenerator.business_logic.data.Todo;
import com.example.jeremy.artgenerator.constants.SqlConstants;
import com.example.jeremy.artgenerator.business_logic.data.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class , Address.class , Album.class , Comment.class , Company.class , Geo.class , Image.class , Post.class , Todo.class} , version = SqlConstants.Databases.DB_VERSION_1 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract AddressDAO addressDAO();
    public abstract AlbumDAO albumDAO();
    public abstract CommentDAO commentDAO();
    public abstract CompanyDAO companyDAO();
    public abstract GeoDAO geoDAO();
    public abstract ImageDAO imageDAO();
    public abstract PostDAO postDAO();
    public abstract TodoDAO todoDAO();
}
