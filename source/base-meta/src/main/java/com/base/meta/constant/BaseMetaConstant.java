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
    public static final Integer GROUP_KIND_PM = 2;
    public static final Integer GROUP_KIND_DEV = 3;
    public static final Integer GROUP_KIND_TESTER = 4;

    public static final Integer MAX_ATTEMPT_FORGET_PWD = 5;
    public static final int MAX_TIME_FORGET_PWD = 5 * 60 * 1000; //5 minutes
    public static final Integer MAX_ATTEMPT_LOGIN = 5;

    public static final Integer PERMISSION_KIND_SYSTEM = 0;
    private BaseMetaConstant() {
        throw new IllegalStateException("Utility class");
    }

    /*
    User Status
     */
    public static final Integer USER_STATUS_ONLEAVE = 1;
    public static final Integer USER_STATUS_AVAILABLE = 2;
    public static final Integer USER_STATUS_BUSY = 3;
    public static final Integer USER_STATUS_ON_SITE_PATERNITY = 4;
    public static final Integer USER_STATUS_RETIRED = 5;
    /*
    Category kind
     */
    public static final Integer CATEGORY_KIND_PROJECT = 1;
    public static final Integer CATEGORY_KIND_ACCOUNT = 2;
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
