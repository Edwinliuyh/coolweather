package com.coolweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell on 2016/3/5.
 *准备建立三张表，Province、City、County，分别
 用于存放省、市、县的各种数据信息，三张表的建表语句分别如下。
 Province：
 create table Province (
 id integer primary key autoincrement,
 province_name text,
 province_code text)
 其中id 是自增长主键，province_name 表示省名，province_code 表示省级代号。

 City：
 create table City (
 id integer primary key autoincrement,
 city_name text,
 city_code text,
 province_id integer)
 其中id 是自增长主键，city_name 表示城市名，city_code 表示市级代号，province_id 是
 City 表关联Province 表的外键。

 County：
 create table County (
 id integer primary key autoincrement,
 county_name text,
 county_code text,
 city_id integer)
 其中id 是自增长主键，county_name 表示县名，county_code 表示县级代号，city_id 是
 County 表关联City 表的外键。

 建表语句就是这样，接下来我们将建表语句写入到代码中。在db 包下新建一个
 CoolWeatherOpenHelper 类
 *
 *
 */
public class CoolWeatherOpenHelper extends SQLiteOpenHelper {
    /**
     * Province表建表语句
     */
    public static final String CREATE_PROVINCE = "create table Province ("
            + "id integer primary key autoincrement, "
            + "province_name text, "
            + "province_code text)";
    /**
     * City表建表语句
     */
    public static final String CREATE_CITY = "create table City ("
            + "id integer primary key autoincrement, "
            + "city_name text, "
            + "city_code text, "
            + "province_id integer)";
    /**
     * County表建表语句
     */
    public static final String CREATE_COUNTY = "create table County ("
            + "id integer primary key autoincrement, "
            + "county_name text, "
            + "county_code text, "
            + "city_id integer)";
    public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE); // 创建Province表
        db.execSQL(CREATE_CITY); // 创建City表
        db.execSQL(CREATE_COUNTY); // 创建County表
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }
}