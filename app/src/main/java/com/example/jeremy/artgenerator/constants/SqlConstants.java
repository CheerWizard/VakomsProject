package com.example.jeremy.artgenerator.constants;

public final class SqlConstants {

    public static final class Databases {
        public static final String APP_DB_NAME = "global_pad_db";
        public static final int DB_VERSION_1 = 1;
    }

    public static final class Tables {
        //tables
        public static final String USER_TB_NAME = "users";
        public static final String SAMPLE_TB_NAME = "samples";
        public static final String PATH_TB_NAME = "paths";
        public static final String DURATION_TB_NAME = "durations";

        public static final class Columns {
            //columns
            //user
            public static final String USER_ID = "user_id";
            public static final String USER_NAME = "user_name";
            public static final String USER_PASSWORD = "user_password";
            public static final String USER_EMAIL = "user_email";
            //sample
            public static final String SAMPLE_ID = "sample_id";
            public static final String SAMPLE_NAME = "sample_name";
            public static final String SAMPLE_PRIORITY = "sample_priority";
            public static final String SAMPLE_PATH_ID = "sample_path_id";
            public static final String SAMPLE_USER_EMAIL = "sample_user_email";
            public static final String SAMPLE_DURATION_ID = "sample_duration_id";
            //paths
            public static final String PATH_ID = "path_id";
            public static final String PATH_BODY = "path_body";
            //durations
            public static final String DURATION_ID = "duration_id";
            public static final String DURATION_TIME = "duration_time";
        }
    }
}
