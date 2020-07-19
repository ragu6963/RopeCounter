package com.example.activitycounter;

import android.provider.BaseColumns;

public class RopeDB {

    public static final class CreateDB implements BaseColumns{
        public static final String DATE = "date";
        public static final String COUNT = "count";
        public static final String WEIGHT = "weight";
        public static final String _TABLENAME0 = "ropecount";
        public static final String _CREATE0 =  "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +DATE+" text not null , "
                +COUNT+" text not null , "
                +WEIGHT+" Integer DEFAULT 75);";
    }
}
