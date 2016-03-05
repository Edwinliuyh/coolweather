package com.coolweather.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是数据库操作的类。
 提供了六组方法，saveProvince()、
 loadProvinces()、saveCity()、loadCities()、saveCounty()、loadCounties()，分别用于存储省份
 数据、读取所有省份数据、存储城市数据、读取某省内所有城市数据、存储县数据、读取某
 市内所有县的数据。
 */
public class CoolWeatherDB {

    /**
     * 数据库名
     */
    public static final String DB_NAME = "cool_weather";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,
                DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取CoolWeatherDB的实例。
     * 此方法是synchronized，即同一时间只能一个线程访问。
     * 如没有操作数据库的CoolWeatherDB类，创建并返回CoolWeatherDB实例。如已有，返回实例。
     */
    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    /**
     * 将Province实例存储到数据库。
     * 封装了insert方法，如果provice不是空值，将provice的name和code存入指定的数据库表格。
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    /**
     * 从数据库读取全国所有的省份Provice信息。
     * 封装了query方法，用cursor遍历指定的数据库表格，将表格上的数据一条一条变为provice对象，
     * 最后返回包含所有provice的List
     */
    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db
                .query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();//新建一个provice对象
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));//获得行号然后转型为Int
                province.setProvinceName(cursor.getString(cursor
                        .getColumnIndex("province_name")));//获得省名然后转型为String
                province.setProvinceCode(cursor.getString(cursor
                        .getColumnIndex("province_code")));//获得省号然后转型为String
                list.add(province);//将provice对象存到List
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将City实例存储到数据库。
     * 封装了insert方法，如果city不是空值，将provice的name和code存入指定的数据库表格。
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    /**
     * 从数据库读取某省下所有的城市信息。
     * 封装了query方法，用cursor遍历指定的数据库表格，将表格上的数据一条一条变为city对象，
     * 最后返回包含所有city的List
     */
    public List<City> loadCities(int provinceId) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id = ?",
                new String[] { String.valueOf(provinceId) }, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor
                        .getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor
                        .getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将County实例存储到数据库。
     * 封装了insert方法，如果city不是空值，将provice的name和code存入指定的数据库表格。
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            values.put("city_id", county.getCityId());
            db.insert("County", null, values);
        }
    }

    /**
     * 从数据库读取某城市下所有的县信息。
     * 封装了query方法，用cursor遍历指定的数据库表格，将表格上的数据一条一条变为County对象，
     * 最后返回包含所有County的List
     */
    public List<County> loadCounties(int cityId) {
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id = ?",
                new String[] { String.valueOf(cityId) }, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor
                        .getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor
                        .getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            } while (cursor.moveToNext());
        }
        return list;
    }

}