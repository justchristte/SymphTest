package com.example.symph.symphtest.database;

import android.app.ActionBar;

import java.util.ArrayList;

/**
 * Created by Kenneth on 8/5/2016.
 */
public class Table {

    static final String TYPE_TEXT="TEXT";
    static final String TYPE_INTEGER_PRIMARY_KEY="INTEGER PRIMARY KEY";
    static final String TYPE_INTEGER="INTEGER";
    static final String TYPE_BLOB="BLOB";

    ArrayList<String>fieldNames;
    ArrayList<String>types;
    String name;

    public Table(String name){
        fieldNames=new ArrayList<>();
        types=new ArrayList<>();
        this.name=name;
    }

    public static Table create(String name){
        return new Table(name);
    }

    public Table addField(String name,String type){
        fieldNames.add(name);
        types.add(type);
        return this;
    }

    public String getCreateQuery(){
        String string="CREATE TABLE IF NOT EXISTS "+name+"(";
        int size=fieldNames.size();
        for(int c=0;c<size;c++){
            string+=fieldNames.get(c)+" "+types.get(c);
            if(c+1<size)
                string+=", ";
        }
        string+=")";
        return string;
    }

    public String[] getProjection(){
        int size=fieldNames.size();
        String[]projection=new String[size];
        for(int c=0;c<size;c++)
            projection[c]=fieldNames.get(c);
        return projection;
    }

    public String getDeleteQuery(){
        return "DROP TABLE IF EXISTS "+name;
    }

}
