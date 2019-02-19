package com.example.jeremy.artgenerator.constants;

public final class ProcessStates {
    //connection statuses
    public static final int STATUS_CONNECTION_FAILED = 0;

    public final static class Successful {
        //profile
        public static final int STATUS_PROFILE_FIELDS_CHANGED = 1;
        //login statuses
        public static final int STATUS_LOGIN_SUCCESSFULLY = 2;
        public static final int STATUS_LOGIN_VALID = 3;
        //register statuses
        public static final int STATUS_REGISTER_VALID = 4;
        public static final int STATUS_REGISTER_SUCCESSFULLY = 5;
        //download statuses
        public static final int STATUS_DOWNLOAD_SUCCESSFULLY = 6;
        //upload statuses
        public static final int STATUS_UPLOAD_SUCCESSFULLY = 7;
        //server statuses
        public static final int STATUS_DELETE_SUCCESS = 8;
        public static final int STATUS_UPDATE_SUCCESS = 9;
        //fragment statuses
        public static final int STATUS_MOVE_TO_SIGN_IN_FRAGMENT = 10;
        public static final int STATUS_MOVE_TO_SIGN_UP_FRAGMENT = 11;
        public static final int STATUS_MOVE_TO_FORGOT_PASSWORD_FRAGMENT = 12;
        public static final int STATUS_MOVE_TO_SLIDESHOW_FRAGMENT = 13;
        //restore statuses
        public static final int STATUS_RESTORE_PASSWORD_SUCCESS = 14;
        //intent statuses
        public static final int STATUS_CAMERA_REQUEST = 15;
        public static final int STATUS_CALL_REQUEST = 16;
        public static final int STATUS_GALLERY_REQUEST = 17;
    }

    public final static class Failed {
        //profile
        public static final int STATUS_PROFILE_FIELDS_EMPTY = -1;
        //login statuses
        public static final int STATUS_LOGIN_GET_RESPONSE_FAILED = -2;
        public static final int STATUS_LOGIN_FIELDS_EMPTY = -3;
        public static final int STATUS_LOGIN_FIELDS_NOT_VALID = -4;
        //register statuses
        public static final int STATUS_REGISTER_GET_RESPONSE_FAILED = -5;
        public static final int STATUS_REGISTER_FIELDS_EMPTY = -6;
        public static final int STATUS_REGISTER_FIELDS_NOT_VALID = -7;
        public static final int STATUS_REGISTER_POST_RESPONSE_FAILED = -8;
        //download statuses
        public static final int STATUS_DOWNLOAD_FAILED = -9;
        //upload statuses
        public static final int STATUS_UPLOAD_FAILED = -10;
        //server statuses
        public static final int STATUS_DELETE_FAILED = -11;
        public static final int STATUS_UPDATE_FAILED = -12;
        //restore statuses
        public static final int STATUS_RESTORE_PASSWORD_FAILED = -13;
        public static final int STATUS_RESTORE_PASSWORD_EMPTY_FIELD = -14;
    }

}
