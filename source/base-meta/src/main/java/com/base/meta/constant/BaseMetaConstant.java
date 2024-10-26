package com.base.meta.constant;


public class BaseMetaConstant {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";


    public static final Integer USER_KIND_ADMIN = 1;
    public static final Integer USER_KIND_PM = 2;
    public static final Integer USER_KIND_DEV = 3;
    public static final Integer USER_KIND_TESTER = 4;

    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_PENDING = 0;
    public static final Integer STATUS_LOCK = -1;
    public static final Integer STATUS_DELETE = -2;

    public static final Integer GROUP_KIND_ADMIN = 1;
    public static final Integer GROUP_KIND_DEV = 2;
    public static final Integer GROUP_KIND_TESTER = 3;

    public static final Integer MAX_ATTEMPT_FORGET_PWD = 5;
    public static final int MAX_TIME_FORGET_PWD = 5 * 60 * 1000; //5 minutes
    public static final Integer MAX_ATTEMPT_LOGIN = 5;

    public static final Integer PERMISSION_KIND_SYSTEM = 0;
    private BaseMetaConstant() {
        throw new IllegalStateException("Utility class");
    }

    /*
    Project Status
     */
    public static final Integer PROJECT_STATUS_NOT_STARTED = 1;
    public static final Integer PROJECT_STATUS_IN_PROGRESS = 2;
    public static final Integer PROJECT_STATUS_COMPLETED = 3;
    public static final Integer PROJECT_STATUS_CANCELLED = 4;

    /*
    User Status
     */
    public static final Integer USER_STATUS_ONLEAVE = 1;
    public static final Integer USER_STATUS_AVAILABLE = 2;
    public static final Integer USER_STATUS_BUSY = 3;
    public static final Integer USER_STATUS_ON_SITE_PATERNITY = 4;
    public static final Integer USER_STATUS_RETIRED = 5;

    /*
    Requirement Status Name
     */
    public static final String REQUIREMENT_STATUS_NAME_DRAFT = "1";
    public static final String REQUIREMENT_STATUS_NAME_IN_VIEW = "2";
    public static final String REQUIREMENT_STATUS_NAME_APPROVED = "3";
    public static final String REQUIREMENT_STATUS_NAME_IMPLEMENTED = "4";

    /*
    Requirement Devision Name
     */
    public static final String REQUIREMENT_DEVISION_NAME_FUNCTIONAL = "1";
    public static final String REQUIREMENT_DEVISION_NAME_DATA = "2";
    public static final String REQUIREMENT_DEVISION_NAME_UI_UX = "3";

    /*
    Requirement Classification Name
     */
    public static final String REQUIREMENT_CLASSIFICATION_NAME_USER_MANAGEMENT = "1";
    public static final String REQUIREMENT_CLASSIFICATION_NAME_RESERVATION_MANAGEMENT = "2";
    public static final String REQUIREMENT_CLASSIFICATION_NAME_OTHER = "3";

    /*
    Program type
     */
    public static final String PROGRAM_TYPE_NAME_NEW_DEVELOPMENT = "N";
    public static final String PROGRAM_TYPE_NAME_MAINTENANCE = "M";
    public static final String PROGRAM_TYPE_NAME_ENHANCEMENT = "E";

    /*
    Category topic
     */
    public static final String CATEGORY_TYPE_PROGRAM = "1";

    /*
    Category kind
     */
    public static final Integer CATEGORY_KIND_ACCOUNT = 1;
    public static final Integer CATEGORY_KIND_PROJECT = 2;
    public static final Integer CATEGORY_KIND_REQUIREMENT = 3;
    public static final Integer CATEGORY_KIND_PROGRAM = 4;
    public static final Integer CATEGORY_KIND_TEST_EXECUTION = 5;
    public static final Integer CATEGORY_KIND_TEST_SUITE_EXECUTION = 6;
    public static final Integer CATEGORY_KIND_TEST_CASE_EXECUTION = 7;
    public static final Integer CATEGORY_KIND_TEST_STEP_EXECUTION = 8;
    public static final Integer CATEGORY_KIND_TEST_DEFECT = 9;

    /*
    Status validation
     */
    public static final Integer STATUS_VALIDATION_NOT_STARTED = 1;
    public static final Integer STATUS_VALIDATION_IN_PROGRESS = 2;
    public static final Integer STATUS_VALIDATION_COMPLETED = 3;
    public static final Integer STATUS_VALIDATION_CANCELLED = 4;



}
