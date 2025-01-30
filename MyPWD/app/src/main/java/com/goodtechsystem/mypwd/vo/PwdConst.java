package com.goodtechsystem.mypwd.vo;

public class PwdConst {

    public static final String DATABASE_NAME =  "goodtechsystem.db";

    public static final int DATABASE_VERSION = 1;

    public static class USER{
        public static final String TABLE_NAME = "t_user";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PASSWORD = "password";
    }

    public static class PWD {
        public static final String TABLE_NAME = "t_pwd";
        public static final String COL_OID = "oid";
        public static final String COL_SITE = "site";
        public static final String COL_ID = "id";
        public static final String COL_PWD = "pwd";
        public static final String COL_PURPOSE = "purpose";
        public static final String COL_USAGE = "usage";
    }

}
