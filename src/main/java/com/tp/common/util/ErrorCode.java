package com.tp.common.util;

public class ErrorCode {
    public final static String ERROR_NOT_DEFINE = "999";

    public final static String ID_SUCCESS = "000001";
    public final static String SUCCESSFUL = "000";

    /**

     * @date 03-11-2015 08:35:55
     * @description Loi nguoi dung 1xx
     */
    public final class ERROR_USER {
        public final static String ERROR_USER_NOT_EXIST = "100"; // Loi user khong ton tai
        public final static String ERROR_USER_PASS_INVALID = "101"; // Loi user mat khau khong hop le
        public final static String ERROR_USER_IN_ACTIVE = "102"; // Loi khong du tham quyen truy cap
        public final static String ERROR_USER_INVALID_FORMAT = "103"; // Loi khong dung dinh dang, loi validate
        public final static String ERROR_USER_NO_PERMISSION = "104"; // Loi nguoi dung chua duoc cap quyen
        public final static String ERROR_USER_DUPLICATE_OBJECT = "105"; // Loi duplicate du lieu
    }

    /**

     * @date 03-11-2015 08:40:37
     * @description Loi lien quan den ket noi 5xx
     */
    public final class ERROR_CONNNECT {
        public final static String ERROR_CONNECT_EX = "501";
        public final static String ERROR_TIMOUT_EX = "503";
        public final static String ERROR_SYSTEM_PROVISIONING = "504";//Loi giao tiep Provisioning
        public final static String ERROR_SYSTEM_INVENTORY = "505";//Loi giao tiep Inventory
        public final static String ERROR_SYSTEM_PRODUCT = "506";//Loi giao tiep Product
        public final static String ERROR_SYSTEM_PAYMENT = "507";//Loi giao tiep Payment
        public final static String ERROR_SYSTEM_BILLING = "508";//Loi giao tiep Billiing
        public final static String ERROR_SYSTEM_NIMS = "509";//Loi giao tiep NIMS
        public final static String ERROR_SYSTEM_QLCTKT = "510";//Loi giao tiep QLCTKT
        public final static String ERROR_SYSTEM_VOFFICE = "511";//Loi giao tiep Voffice
        public final static String ERROR_SYSTEM_ERP = "512";//Loi giao tiep ERP tai chinh
        public final static String ERROR_SYSTEM_LOGISTIC = "513";//Loi giao tiep Logistic
        public final static String ERROR_SYSTEM_ROAMING = "514"; // Loi giao tiep Roaming
        public final static String ERROR_SYSTEM_PRIVILEGE = "515"; // Loi giao tiep PRIVILEGE
        public final static String ERROR_SYSTEM_HMSD_WS = "516"; // Loi giao tiep HMSD
        public final static String ERROR_SYSTEM_SALE = "517"; // Loi giao tiep HMSD
        public final static String ERROR_SYSTEM_CC = "518"; // Loi giao tiep CC
        public final static String ERROR_SYSTEM_KTTS = "519"; // Loi giao tiep voi he thong kho tang tai san
        public final static String ERROR_SYSTEM_VAS = "520"; // Loi giao tiep VASDATA
        public final static String ERROR_CONN_SUCCESS_TERMINATE_FAILED_SALE = "-18"; // ma loi dau noi theu bao co dinh thanh cong nhung cham dut that bai
    }

    /**
     * @date 03-11-2015 08:37:31
     * @description Loi lap trinh 2xx va 3xx
     */
    public final class ERROR_STANDARD {
        public final static String ERROR_NULL_POINTER_EX = "200";
        public final static String ERROR_ARRAY_OUT_OF_BOUNDS_EX = "201";
        public final static String ERROR_CLASS_NOT_FOUND = "202";
        public final static String ERROR_NUMBER_FORMAT_EX = "203";
        public final static String ERROR_PARSE_EX = "204";
        public final static String ERROR_CLASS_CAST_EX = "205";
        public final static String ERROR_VALIDATE_INPUT = "206";
        public final static String ERROR_DUPLICATE = "1";
        public final static String SUCCESS = "0";
        public final static String ERROR_EXCEPTION = "2";
        public final static String ERROR_UPDATE = "207";
        public final static String ERROR_INSERT = "208";
        public final static String ERROR_DELETE = "209";
        public final static String ERROR_SAVE_OBJECT_DATABASE = "210";
        public final static String ERROR_INNER_VALIDATE = "211";
    }


    /**
     * @date 03-11-2015 08:38:42
     * @description Loi bo nho 4xx
     */
    public final class ERROR_MEMORY {

    }


    /**
     * @date 03-11-2015 08:41:43
     * @description loi lien quan den thao tac file 6xx
     */
    public final class ERROR_HANDLE_FILE {
        public final static String ERROR_FILE_NOT_FOUND_EX = "600";

    }


}