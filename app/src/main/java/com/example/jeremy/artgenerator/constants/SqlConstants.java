package com.example.jeremy.artgenerator.constants;

public final class SqlConstants {

    public static final class Databases {
        public static final String APP_DB_NAME = "project_db";
        public static final int DB_VERSION_1 = 1;
    }

    public static final class Tables {
        //tables
        public static final String USER_TB_NAME = "users";
        public static final String ADDRESS_TB_NAME = "addresses";
        public static final String ALBUM_TB_NAME = "albums";
        public static final String COMMENT_TB_NAME = "comments";
        public static final String COMPANY_TB_NAME = "companies";
        public static final String GEO_TB_NAME = "geo";
        public static final String IMAGE_TB_NAME = "images";
        public static final String POST_TB_NAME = "posts";
        public static final String TODO_TB_NAME = "todos";
        public static final String SYSTEM_TB_NAME = "system";

        public static final class Columns {
            //columns
            //user
            public static final String USER_ID = "user_id";
            public static final String USER_NAME = "user_name";
            public static final String USER_PASSWORD = "user_password";
            public static final String USER_EMAIL = "user_email";
            public static final String USER_LOGIN = "user_login";
            public static final String USER_PHONE = "user_phone";
            public static final String USER_WEBSITE = "user_website";
            //address
            public static final String ADDRESS_ID = "address_id";
            public static final String ADDRESS_STREET = "address_street";
            public static final String ADDRESS_SUITE = "address_suite";
            public static final String ADDRESS_CITY = "address_city";
            public static final String ADDRESS_ZIPCODE = "address_zipcode";
            //album
            public static final String ALBUM_ID = "album_id";
            public static final String ALBUM_TITLE = "album_title";
            //comment
            public static final String COMMENT_ID = "comment_id";
            public static final String COMMENT_NAME = "comment_name";
            public static final String COMMENT_EMAIL = "comment_email";
            public static final String COMMENT_BODY = "comment_body";
            //company
            public static final String COMPANY_ID = "company_id";
            public static final String COMPANY_NAME = "company_name";
            public static final String COMPANY_CATCH_PHRASE = "company_catch_phrase";
            public static final String COMPANY_BS = "company_bs";
            //geo
            public static final String GEO_ID = "geo_id";
            public static final String GEO_LON = "geo_lon";
            public static final String GEO_LAT = "geo_lat";
            //image
            public static final String IMAGE_ID = "image_id";
            public static final String IMAGE_TITLE = "image_title";
            public static final String IMAGE_URL = "image_url";
            public static final String IMAGE_THUMBNAIL_URL = "image_thumbnail_url";
            //post
            public static final String POST_ID = "post_id";
            public static final String POST_TITLE = "post_title";
            public static final String POST_BODY = "post_body";
            //todo
            public static final String TODO_ID = "todo_id";
            public static final String TODO_TITLE = "todo_title";
            public static final String TODO_COMPLETED = "todo_completed";
            //system
            public static final String SYSTEM_ID = "system_id";
            public static final String SYSTEM_USERS_COUNT = "system_users_count";
        }
    }
}
