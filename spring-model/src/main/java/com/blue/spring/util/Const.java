package com.blue.spring.util;

import java.util.HashMap;
import java.util.Map;

public class Const {
    public final static String ALL_VALUE = "-1";
    public final static String VALUE_DECOLLATOR = ",";
    public final static String KEY_DECOLLATOR = "-";
    public final static String SEPARATE_XIE = "/";
    public final static String DOT_STRING = ".";
    public static final String THUMB = "thumb";
    public static final String STAR = "\\*";
    public static final String BLANK_STRING = "";
    public static final String VALUE_POINT=".";
    public static final String WEN_HAO="?";
    public static final String XIA_HUA_XIAN="_";
    public static final String UP_IMAGE="img";
    public static final String DOMAIN_PORT=":";

    public static final int INIT_NUM = 0;

    // 负整数
    public final static String INTEGER = "^\\-*\\d*$";
    // 非负整数
    public final static String POSITIVE_INTEGER = "^[1-9]\\d*|0$";
    // 正整数
    public final static String NON_ZERO_POSITIVE_INTEGER = "^[1-9]\\d*$";
    // 正数
    public final static String NON_ZERO_POSITIVE_NUMBER = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|[1-9]\\d*$";


    /************************** redis key begin *******************************/
    /** 视频redis key */
    public static final String REDIS_KEY_VIDEO="v-";

    /** ALBUM redis key */
    public static final String REDIS_KEY_ALBUM="a-";

    /** CATEGORY redis key */
    public static final String REDIS_KEY_CATEGORY="c-";

    /** CUSTOM_CATEGORY redis key */
    public static final String REDIS_KEY_CATEGORY_CUSTOM="c-c-";

    /** CUSTOM redis key */
    public static final String REDIS_KEY_CUSTOMER="u-";

    /** SP redis key */
    public static final String REDIS_KEY_SP="sp-";

    /** file redis key */
    public static final String REDIS_KEY_FILE="f-";

    /** file by video redis key */
    public static final String REDIS_KEY_FILES_VIDEO="f-v-";

    /** ALL MENU redis key */
    public static final String REDIS_KEY_MENU_ALL="menu-all";

    /** simple MENU redis key */
    public static final String REDIS_KEY_MENU="menu-";

    public static final String REDIS_KEY_ROLE="role-";

    public static final String REDIS_KEY_ROLE_PERM="role-perm-";

    public static final String REDIS_KEY_ROLE_SP="role-sp-";

    public static final String REDIS_KEY_COUNT_7DAY_UPLOAD="count-upload-7-";

    public static final String REDIS_KEY_COUNT_7DAY_PUBLISH_SUCESS="count-publish-s-7-";

    public static final String REDIS_KEY_COUNT_7DAY_PUBLISH_FAILD="count-publish-f-7-";

    public static final String REDIS_KEY_COUNT_ALBUM="count-album-";

    public static final String REDIS_KEY_COUNT_GROUP_CATEGORY="count-group-category-";

    public static final String REDIS_KEY_COUNT_GROUP_STATUS="count-group-status-";

    public static final String REDIS_KEY_STORAGE="storage-";

    public static final String REDIS_KEY_STORAGE_ALL_SP="storage-all-sp-";

    public static final String REDIS_KEY_STORAGE_ENABLE_SP="storage-enable-sp-";

    public static final String REDIS_KEY_STORAGE_CONFIG="storage-config-";

    public static final String REDIS_KEY_STORAGE_CONFIGS="storage-configs-";

    public static final String REDIS_KEY_RELATION="relation-relation-";

    public static final String REDIS_KEY_RELATION_CATEGORY_SP="relation-category-sp-";

    public static final String REDIS_KEY_RELATION_CUSTOMCATEGORY_SP="relation-customcategory-sp-";

    public static final String REDIS_KEY_COUNT_PV="count-pv-";

    public static final String REDIS_KEY_STORAGE_DOMAIN_WEIGHT="storage-domainWeight-";

    public static final String REDIS_KEY_SP_EXP_ID="sp-exp-id-";

    public static final String REDIS_KEY_SP_EXP_SPID="sp-exp-spid-";

    /************************** redis key end *******************************/

    public static final String http = "http" ;
    public static final String ftp = "ftp" ;
    public static final String http_append_mh = ":" ;
    public static final String http_append_xg = "//";

}
