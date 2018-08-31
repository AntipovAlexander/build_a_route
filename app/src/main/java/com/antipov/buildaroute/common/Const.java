package com.antipov.buildaroute.common;

public class Const {
    public static int RETRY_COUNT = 5;
    public static String BASE_URL = "https://maps.googleapis.com/maps/api/";

    public static class Args {
        public static String SELECTED_ADDRESS = "selected-address";
    }

    public static class Requests {
        public static final int REQUEST_GET_START = 1;
        public static final int REQUEST_GET_FINISH = 2;
        public static final int REQUEST_GET_ADDRESS = 3;
    }
}
