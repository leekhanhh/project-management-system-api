package com.base.meta.dto;

public class ErrorCode {
    /**
     * General error code
     */
    public static final String GENERAL_ERROR_REQUIRE_PARAMS = "ERROR-GENERAL-0000";
    public static final String GENERAL_ERROR_STORE_LOCKED = "ERROR-GENERAL-0001";
    public static final String GENERAL_ERROR_ACCOUNT_LOCKED = "ERROR-GENERAL-0002";
    public static final String GENERAL_ERROR_SHOP_LOCKED = "ERROR-GENERAL-0003";
    public static final String GENERAL_ERROR_STORE_NOT_FOUND = "ERROR-GENERAL-0004";
    public static final String GENERAL_ERROR_ACCOUNT_NOT_FOUND = "ERROR-GENERAL-0005";
    public static final String ERROR_DATE_INVALID = "ERROR-GENERAL-0006";

    /**
     * Starting error code Account
     */
    public static final String ACCOUNT_ERROR_UNKNOWN = "ERROR-ACCOUNT-000";
    public static final String ACCOUNT_ERROR_USERNAME_EXIST = "ERROR-ACCOUNT-001";
    public static final String ACCOUNT_ERROR_NOT_FOUND = "ERROR-ACCOUNT-002";
    public static final String ACCOUNT_ERROR_WRONG_PASSWORD = "ERROR-ACCOUNT-003";
    public static final String ACCOUNT_ERROR_WRONG_HASH_RESET_PASS = "ERROR-ACCOUNT-004";
    public static final String ACCOUNT_ERROR_LOCKED = "ERROR-ACCOUNT-005";
    public static final String ACCOUNT_ERROR_OTP_INVALID = "ERROR-ACCOUNT-006";
    public static final String ACCOUNT_ERROR_LOGIN = "ERROR-ACCOUNT-007";
    public static final String ACCOUNT_ERROR_MERCHANT_LOGIN_ERROR_DEVICE = "ERROR-ACCOUNT-008";
    public static final String ACCOUNT_ERROR_MERCHANT_LOGIN_ERROR_STORE = "ERROR-ACCOUNT-009";
    public static final String ACCOUNT_ERROR_MERCHANT_LOGIN_WRONG_STORE = "ERROR-ACCOUNT-010";
    public static final String ACCOUNT_ERROR_MERCHANT_SERVICE_NOT_REGISTER = "ERROR-ACCOUNT-011";
    public static final String ACCOUNT_ERROR_NOT_ALLOW_DELETE_SUPPER_ADMIN = "ERROR-ACCOUNT-012";

    public static final String ACCOUNT_ERROR_NOT_KIND_DEV = "ERROR-ACCOUNT-013";
    public static final String ACCOUNT_ERROR_NOT_KIND_TESTER = "ERROR-ACCOUNT-014";
    public static final String ACCOUNT_ERROR_NOT_KIND_PM = "ERROR-ACCOUNT-015";

    /**
     * Starting error code USER
     */
    public static final String USER_ERROR_EXIST = "ERROR-USER-000";
    public static final String USER_ERROR_NOT_FOUND = "ERROR-USER-001";
    public static final String USER_ERROR_LOGIN_FAILED = "ERROR-USER-002";

    /**
     * Starting error code DATABASE_ERROR
     */
    public static final String ERROR_DB_QUERY = "ERROR-DB-QUERY-0000";

    /**
     * Permission error code
     */
    public static final String PERMISSION_ERROR_NAME_EXIST = "ERROR-PERMISSION-000";
    public static final String PERMISSION_ERROR_CODE_EXIST = "ERROR-PERMISSION-001";
    public static final String PERMISSION_ERROR_NOT_FOUND = "ERROR-PERMISSION-002";

    /**
     * Group error code
     */
    public static final String GROUP_ERROR_NAME_EXIST = "ERROR-GROUP-000";
    public static final String GROUP_ERROR_NOT_FOUND = "ERROR-GROUP-001";
    public static final String GROUP_ERROR_KIND_EXIST = "ERROR-GROUP-002";

    /**
     * User error code
     */
    public static final String USER_ERROR_UNAUTHORIZED = "ERROR-USER-003";
    public static final String USER_ERROR_USERNAME_EXIST = "ERROR-USER-004";
    public static final String USER_ERROR_GROUP_NOT_FOUND = "ERROR-USER-005";
    public static final String USER_ERROR_TENANT_ID_EXIST = "ERROR-USER-006";
    public static final String USER_ERROR_WRONG_PASSWORD = "ERROR-USER-007";
    public static final String USER_ERROR_KIND_INVALID = "ERROR-USER-008";
    public static final String USER_ERROR_NOT_AVAILABLE = "ERROR-USER-009";
    public static final String USER_ERROR_ONLEAVE = "ERROR-USER-010";
    public static final String USER_ERROR_BUSY = "ERROR-USER-011";
    public static final String USER_ERROR_ON_SITE_PATERNITY = "ERROR-USER-012";
    public static final String USER_ERROR_RETIRED = "ERROR-USER-013";
    public static final String USER_ERROR_IN_PROJECT = "ERROR-USER-014";


    /**
     * Project error code
     */
    public static final String PROJECT_ERROR_NAME_DUPLICATED = "ERROR-PROJECT-000";
    public static final String PROJECT_ERROR_NAME_NOT_EXIST = "ERROR-PROJECT-001";
    public static final String PROJECT_ERROR_NOT_EXIST = "ERROR-PROJECT-002";


    /**
     * Project member error code
     */
    public static final String PROJECT_MEMBER_ERROR_NOT_EXIST = "ERROR-PROJECT-MEMBER-000";
    public static final String PROJECT_MEMBER_ERROR_ONBOARDED_DATE_AFTER_OFFBOARDED_DATE = "ERROR-PROJECT-MEMBER-001";

    /**
     * Requirement error code
     */
    public static final String REQUIREMENT_ERROR_NOT_FOUND = "ERROR-REQUIREMENT-000";
    public static final String REQUIREMENT_ERROR_NAME_EXISTED = "ERROR-REQUIREMENT-001";


    /**
     * Category error code
     */
    public static final String CATEGORY_ERROR_NOT_FOUND = "ERROR-CATEGORY-000";
    public static final String CATEGORY_ERROR_KIND_EXIST = "ERROR-CATEGORY-001";
    public static final String CATEGORY_ERROR_NAME_EXIST_IN_KIND = "ERROR-CATEGORY-001";
    public static final String CATEGORY_ERROR_CODE_EXIST = "ERROR-CATEGORY-002";

    /**
     * Program error code
     */
    public static final String PROGRAM_ERROR_NAME_EXIST = "ERROR-PROGRAM-000";
    public static final String PROGRAM_ERROR_NOT_EXIST = "ERROR-PROGRAM-001";

    /**
     * Test case error code
     */
    public static final String TEST_CASE_ERROR_NAME_EXISTED = "ERROR-TEST-CASE-000";
    public static final String TEST_CASE_ERROR_NOT_EXIST = "ERROR-TEST-CASE-001";

    /**
     * Test case upload error code
     */
    public static final String TEST_CASE_UPLOAD_ERROR_NOT_EXIST = "ERROR-TEST-CASE-UPLOAD-000";
    public static final String TEST_CASE_UPLOAD_ERROR_NAME_EXISTED = "ERROR-TEST-CASE-UPLOAD-001";

    /**
     * Test step error code
     */
    public static final String TEST_STEP_ERROR_NOT_EXIST = "ERROR-TEST-STEP-000";
    public static final String TEST_STEP_ERROR_NAME_EXISTED = "ERROR-TEST-STEP-001";
    public static final String TEST_STEP_ERROR_STEP_NUMBER_EXISTED = "ERROR-TEST-STEP-002";

    /**
     * Test plan error code
     */
    public static final String TEST_PLAN_ERROR_NAME_EXISTED = "ERROR-TEST-PLAN-000";
    public static final String TEST_PLAN_ERROR_NOT_EXIST = "ERROR-TEST-PLAN-001";
    public static final String TEST_PLAN_ERROR_DATE_INVALID = "ERROR-TEST-PLAN-002";

    /**
     * Test suite error code
     */
    public static final String TEST_SUITE_ERROR_NOT_EXIST = "ERROR-TEST-SUITE-000";

    /**
     * Test suite test case relation error code
     */
    public static final String TEST_SUITE_TEST_CASE_RELATION_ERROR_EXIST = "ERROR-TEST-SUITE-TEST-CASE-RELATION-000";

    /**
     * Test plan test suite relation error code
     */
    public static final String TEST_PLAN_TEST_SUITE_RELATION_ERROR_NOT_EXIST = "ERROR-TEST-PLAN-TEST-SUITE-RELATION-000";

    /**
     * Test execution error code
     */
    public static final String TEST_EXECUTION_ERROR_EXISTED = "ERROR-TEST-EXECUTION-000";
    public static final String TEST_EXECUTION_ERROR_NOT_EXIST = "ERROR-TEST-EXECUTION-001";

    /**
     * Test execution turn error code
     */
    public static final String TEST_EXECUTION_TURN_ERROR_EXIST = "ERROR-TEST-EXECUTION-TURN-000";
    public static final String TEST_EXECUTION_TURN_ERROR_NOT_EXIST = "ERROR-TEST-EXECUTION-TURN-001";

    /**
     * Test case execution error code
     */
    public static final String TEST_CASE_EXECUTION_ERROR_NOT_EXIST = "ERROR-TEST-CASE-EXECUTION-000";

    /**
     * Test defect error code
     */
    public static final String TEST_DEFECT_ERROR_NOT_EXIST = "ERROR-TEST-DEFECT-000";

    /**
     * Test defect error code
     */
    public static final String TEST_DEFECT_FIXED_RESULT_ERROR_NOT_EXIST = "ERROR-TEST-DEFECT-FIXED-RESULT-000";

    /**
     * Test step execution error code
     */
    public static final String TEST_STEP_EXECUTION_ERROR_NOT_EXIST = "ERROR-TEST-STEP-EXECUTION-000";

    /**
     * Test suite execution error code
     */
    public static final String TEST_SUITE_EXECUTION_ERROR_ORDER_NUMBER_EXISTED = "ERROR-TEST-SUITE-EXECUTION-000";
    public static final String TEST_SUITE_EXECUTION_ERROR_NOT_EXIST = "ERROR-TEST-SUITE-EXECUTION-001";

    /**
     * Test defect comment error code
     */
    public static final String TEST_DEFECT_COMMENT_ERROR_NOT_EXIST = "ERROR-TEST-DEFECT-COMMENT-000";
}
