package com.example.jeremy.artgenerator.constants;

public final class ProcessStates {
    //connection statuses
    public static final int STATUS_CONNECTION_FAILED = 0;

    public final static class Successful {
        //recorder statuses
        public static final int STATUS_RECORDER_START = 1;
        public static final int STATUS_RECORDER_STOP = 2;
        //player statuses
        public static final int STATUS_PLAYER_START = 4;
        public static final int STATUS_PLAYER_STOP = 5;
        public static final int STATUS_PLAYER_PAUSE = 6;
        //rewriter statuses
        public static final int STATUS_SAMPLE_PRIORITY_CHANGED = 7;
        public static final int STATUS_SAMPLE_FIELDS_CHANGED = 8;
        public static final int STATUS_PROFILE_FIELDS_CHANGED = 9;
        //login statuses
        public static final int STATUS_LOGIN_SUCCESSFULLY = 12;
        //register statuses
        public static final int STATUS_REGISTER_SUCCESSFULLY = 13;
        //download statuses
        public static final int STATUS_DOWNLOAD_START = 14;
        public static final int STATUS_DOWNLOAD_FINISH = 15;
        //upload statuses
        public static final int STATUS_UPLOAD_START = 16;
        public static final int STATUS_UPLOAD_FINISH = 17;
        //server statuses
        public static final int STATUS_DELETE_SUCCESS = 18;
    }

    public final static class Failed {
        //recorder statuses
        public static final int STATUS_RECORD_NOT_FOUND = -1;
        //player statuses
        public static final int STATUS_SOUND_NOT_FOUND = -2;
        //rewriter statuses
        public static final int STATUS_SAMPLE_PRIORITY_OUT_OF_BOUNDS = -3;
        public static final int STATUS_SAMPLE_FIELDS_EMPTY = -4;
        public static final int STATUS_PROFILE_FIELDS_EMPTY = -5;
        //login statuses
        public static final int STATUS_LOGIN_GET_RESPONSE_FAILED = -6;
        public static final int STATUS_LOGIN_FIELDS_EMPTY = -7;
        public static final int STATUS_LOGIN_FIELDS_NOT_VALID = -8;
        //register statuses
        public static final int STATUS_REGISTER_GET_RESPONSE_FAILED = -9;
        public static final int STATUS_REGISTER_FIELDS_EMPTY = -10;
        public static final int STATUS_REGISTER_FIELDS_NOT_VALID = -11;
        public static final int STATUS_REGISTER_POST_RESPONSE_FAILED = -12;
        //download statuses
        public static final int STATUS_DOWNLOAD_FAILED = -13;
        //upload statuses
        public static final int STATUS_UPLOAD_FAILED = -14;
        //server statuses
        public static final int STATUS_DELETE_FAILED = -15;
        public static final int STATUS_UPDATE_FAILED = -16;
        //pending intent statuses
        public static final int STATUS_PENDING_INTENT_CANCELED = -17;
    }

}
