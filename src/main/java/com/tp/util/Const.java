package com.tp.util;

/**
 * Created by hopnv on 20/07/2017.
 */
public final class Const {

    public static final class LANGUAGE {
        public static final String BUNDLE_NAME = "i18n.lang";
        public static final String BUNDLE_EXTENSION = "properties";
        public static final String CHARSET = "UTF-8";
        public static final String SYSTEM_CONFIG = "system-config.properties";
    }

    public static final class SORT {
        public static final String ASC = "ASC";
        public static final String ASCENDING = "ASCENDING";
        public static final String DESCENDING = "DESCENDING";
        public static final String DESC = "DESC";
    }

    public static final class STATUS {
        public static final Integer ACTIVCE = 1;
        public static final Integer INACTIVCE = 0;
    }

    public static final class AREA {
        public static final String PROVINCE = "PROVINCE";
        public static final String DISTRICT = "DISTRICT";
    }

    public static final class MENU {
        public static final Integer MODULE = 1;
        public static final Integer COMPONENT = 2;
    }
    public final static String ROLE = "ROLE_";
    public static class PASSWORD_RECEIVER_TYPE {
        public final static Integer FORGOT_PASSWORD = 1; //user quen mat khau
        public final static Integer RESET_PASSWORD  = 2; // admin reset password cho user
        public final static Integer CREATE_USER     = 3; // user vua moi tao
    }

    public static class PASSWORD_RECEIVER_STATUS {
        public final static Integer NEW             = 1; // chua gui email cho nguoi dung
        public final static Integer PROCESSING      = 2;
        public final static Integer DONE            = 0; // da gui email thanh cong cho nguoi dung
    }

    public static final class ACTION_CODE {
        public static final String ACTION_CODE_CATALOG_MOTO = "ACTION_CODE_CATALOG_MOTO";
        public static final String ACTION_CODE_CATALOG_BRAND = "ACTION_CODE_CATALOG_BRAND";
        public static final String ACTION_CODE_CATALOG_ACCESSARY = "ACTION_CODE_CATALOG_ACCESSARY";
        public static final String ACTION_CODE_CATALOG_SOURCE_INPUT = "ACTION_CODE_CATALOG_SOURCE_INPUT";
        public static final String ACTION_CODE_CATALOG_COLOR = "ACTION_CODE_CATALOG_COLOR";
        public static final String ACTION_CODE_CATALOG_LEVEL_ERROR = "ACTION_CODE_CATALOG_LEVEL_ERROR";
        public static final String ACTION_CODE_CATALOG_REASON_TO_MOVE = "ACTION_CODE_CATALOG_REASON_TO_MOVE";
        public static final String ACTION_CODE_CATALOG_JOB = "ACTION_CODE_CATALOG_JOB";
        public static final String ACTION_CODE_CATALOG_LEVEL_GLAD = "ACTION_CODE_CATALOG_LEVEL_GLAD";
        public static final String ACTION_CODE_CATALOG_RELATIONSHIP = "ACTION_CODE_CATALOG_RELATIONSHIP";
        public static final String ACTION_CODE_CATALOG_SERVICE = "ACTION_CODE_CATALOG_SERVICE";
        public static final String ACTION_CODE_CATALOG_GENUS_MOTOR = "ACTION_CODE_CATALOG_GENUS_MOTOR";
        public static final String ACTION_CODE_CATALOG_SOURCE_CLIENTAGE = "ACTION_CODE_CATALOG_SOURCE_CLIENTAGE";
        public static final String ACTION_CODE_CATALOG_CLASSIFICATION = "ACTION_CODE_CATALOG_CLASSIFICATION";

        public static final String ACTION_CODE_CATALOG_UNIT = "ACTION_CODE_CATALOG_UNIT";
        public static final String ACTION_CODE_CATALOG_MANUFACTURE = "ACTION_CODE_CATALOG_MANUFACTURE";
        public static final String ACTION_CODE_CATALOG_VEHICLE = "ACTION_CODE_CATALOG_VEHICLE";
        public static final String PRIORITY_LEVEL = "PRIORITY_LEVEL";
        public static final String ACTION_CODE_SERVICE_TYPE = "ACTION_CODE_SERVICE_TYPE";
        public static final String ACTION_CODE_TYPE_CUSTOMER_CARE = "ACTION_CODE_TYPE_CUSTOMER_CARE";
        public static final String ACTION_CODE_FORM_CUSTOMER_CARE = "ACTION_CODE_FORM_CUSTOMER_CARE";

        public static final String ACTION_CODE_REPORT = "ACTION_CODE_REPORT";
        public static final String ACTION_CODE_TYPE_INPUT = "ACTION_CODE_TYPE_INPUT";

        public static final String ACTION_CODE_CATALOG_POSITION = "ACTION_CODE_CATALOG_POSITION";
        public static final String ACTION_CODE_CATALOG_PART_OF_BRAND = "ACTION_CODE_CATALOG_PART_OF_BRAND";
        public static final String ACTION_CODE_CATALOG_TRANSPORT = "ACTION_CODE_CATALOG_TRANSPORT";
        public static final String ACTION_CODE_CATALOG_FOOD = "ACTION_CODE_CATALOG_FOOD";
        public static final String ACTION_CODE_CATALOG_SUPPLIER = "ACTION_CODE_CATALOG_SUPPLIER";
        public static final String ACTION_CODE_CATALOG_LEVEL = "ACTION_CODE_CATALOG_LEVEL";
        //public static final String ACTION_CODE_CATALOG_PARTS = "ACTION_CODE_CATALOG_PARTS";
        //public static final String ACTION_CODE_CATALOG_WORK_LOCATION = "ACTION_CODE_CATALOG_WORK_LOCATION";

    }

    public static final class CUSTOMER_PERMISSION {
        public final static String MTT_INPUT_EDIT_CUSTOMER_PERMISSION = "MTT_INPUT_EDIT_CUSTOMER_PERMISSION";
        public final static String MTT_INPUT_EDIT_CAR_PERMISSION = "MTT_INPUT_EDIT_CAR_PERMISSION";
        public final static String MTT_INPUT_DELETE_CAR_PERMISSION = "MTT_INPUT_DELETE_CAR_PERMISSION";
        public final static String MTT_THUNGAN_SHOW_ALL_TICKET_PERMISSION = "MTT_THUNGAN_SHOW_ALL_TICKET_PERMISSION";
        public final static String HR_UPDATE_STAFF_PERMISSION = "HR_UPDATE_STAFF_PERMISSION";
        public final static String HR_ADD_STAFF_PERMISSION = "HR_ADD_STAFF_PERMISSION";
        public final static String HR_VIEW_ALL_STAFF_MANAGERMENT = "HR_VIEW_ALL_STAFF_MANAGERMENT";
    }

    public static final class DATE_FORMAT {
        public static final String YYYYMMDD = "yyyy-MM-dd";
        public static final String DDMMYYYY = "dd/MM/yyyy";
        public static final String DDMMYYYYHHMM = "dd/MM/yyyy HH:mm";

    }

    public static final class REGEX{
        public static final String CODE="[0-9a-zA-Z_-]+";
        public static final String NUMBER="(^[0-9]+$|^$)";

    }
    public static final class AP_DOMAIN{
        public static final String YEAR_TYPE="YEAR_TYPE";
        public static final String MONTH_TYPE="MONTH_TYPE";
        public static final String SUPPLIER_TYPE="SUPPLIER_TYPE";
        public static final String SALE_DSA_MEETING_RESULT = "SALE_DSA_MEETING_RESULT";
        public static final String SALE_DSA_CAN_NOT_CONTACT = "SALE_DSA_CAN_NOT_CONTACT";
        public static final String SALE_DSA_REASON = "SALE_DSA_REASON";
        public static final String SALE_CCA_ACCEPT_STATUS = "SALE_CCA_ACCEPT_STATUS";
        public static final String SALE_PRODUCT_TYPE = "SALE_PRODUCT_TYPE";
        public static final String SALE_BO_LIMIT_TS_RISK = "SALE_BO_LIMIT_TS_RISK";
        public static final String SALE_BO_STATUS_CHECK_UP = "SALE_BO_STATUS_CHECK_UP";
        public static final String SALE_BO_QDE_STATUS_CHECK_UP = "SALE_BO_QDE_STATUS_CHECK_UP";
        public static final String SALE_BO_STATUS_REASON_CHECK_UP = "SALE_BO_STATUS_REASON_CHECK_UP";
        public static final String SALE_BO_FOLLOW_STATUS_DOC_VPBANK = "SALE_BO_FOLLOW_STATUS_DOC_VPBANK";
        public static final String SALE_BO_FOLLOW_GROUP_STATUS_DOC = "SALE_BO_FOLLOW_GROUP_STATUS_DOC";
        public static final String SALE_BO_FOLLOW_DETAIL_STATUS_DOC = "SALE_BO_FOLLOW_DETAIL_STATUS_DOC";
        public static final String SALE_BO_FOLLOW_DOC_RETURN_STATUS = "SALE_BO_FOLLOW_DOC_RETURN_STATUS";
        public static final String SALE_BO_SEND_TO_FOLLOW_STATUS = "SALE_BO_SEND_TO_FOLLOW_STATUS";
        public static final String SALE_BI_CONFIRM_ERROR_BO_CHECKUP = "SALE_BI_CONFIRM_ERROR_BO_CHECKUP";
        public static final String SALE_USER_ROLE = "SALE_USER_ROLE";
        public static final String SALE_BO_CHECKUP_BOUND_CODE_DSA = "SALE_BO_CHECKUP_BOUND_CODE_DSA";

    }

    public static final class ROLE_USER{
        public static final String SP_DSA="SP_DSA";

    }

    public static final class TICKET_STATE {
        public static final int DRAFT = 1;
        public static final int PENDING_APPROACH = 2;
        public static final int APPROACH = 3;
        public static final int REJECT = 0;
    }

    public static final class SERVICE_TYPE {
        public static final int SPARE_PART_TYPE = 0;
        public static final int SERVICE_TYPE = 1;
    }

    public static final class VEHICLE_TYPE {
        public static final int VEHICLE_TYPE = 0;
        public static final int BUY_TYPE = 1;
    }

    public static final class OPTION {
        public static final int IMPORT = 1;
        public static final int EXPORT = 2;
        public static final int STATISTICS = 3;
    }

    public static final class SERVICE_TICKET_STATE {
        public final static int DRAFT = 0;
        public final static int DONE = 1;
        public final static int EXPOSE = 2;
    }

    public static final class PERSISTENCE {
        public final static String SALE_PORTAL = "SALE_PORTAL";
        public final static String DHW = "DHW";
        public final static String CRM_HN = "CRM_HN";
        public final static String CRM_HCM = "CRM_HCM";
    }

    public static final class ROLE_TYPE_TEAM {
        public final static Long TEAM_247 = 1l;
        public final static Long CCA = 2l;
    }

    public static final class ROLE_NAME_TEAM {
        public final static String BO_CHECKUP = "BO_CHECKUP";
        public final static String BO_FOLLOW = "BO_FOLLOW";
    }

    public static final String FACES_REQUEST_HEADER = "faces-request";
    public static final String STAFF_INFO = "staff_info";
    public static final String USER_INFO = "user_info";
    public static final String AUTHENTICATED = "AUTHENTICATED";
    public static final String ROLE_MODULE = "ROLE_MODULE";
    public static final String ROLE_COMPONENT = "ROLE_COMPONENT";
    public static final String MENU_TREE = "MENU_TREE";
    public static final String BYPASS_RESOURCE = "/javax.faces.resource";

}
