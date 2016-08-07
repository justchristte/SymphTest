package com.example.symph.symphtest.database;

import java.util.ArrayList;

public class Table {

    interface FieldTypes {
        String TYPE_TEXT="TEXT";
        String TYPE_INTEGER_PRIMARY_KEY="INTEGER PRIMARY KEY";
        String TYPE_INTEGER="INTEGER";
        String TYPE_BLOB="BLOB";
    }

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

    public String[] getProjection(){
        int size=fieldNames.size();
        String[]projection=new String[size];
        for(int c=0;c<size;c++)
            projection[c]=fieldNames.get(c);
        return projection;
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

    public String getInsertStatementExcept(String[]insertException){
        String sql="insert into "+name+" (";
        String fieldName;
        ArrayList<String>questionMarks=new ArrayList<>();
        String questionMark;
        int size=fieldNames.size();

        for(int c=0;c<size;c++){
            fieldName=fieldNames.get(c);
            if(!inExeption(fieldName,insertException)){
                sql+=fieldName;
                questionMark="?";
                if(c+1<size){
                    sql+=",";
                    questionMark+=",";
                }
                questionMarks.add(questionMark);
            }
            sql+="";
        }
        sql+=") values(";
        for (int c=0;c<questionMarks.size();c++)
            sql+=questionMarks.get(c);
        sql+=")";
        return sql;
    }

    public boolean inExeption(String fieldName,String[]insertException){
        for (int c=0;c<insertException.length;c++)
            if(fieldName.toLowerCase().equals(insertException[c].toLowerCase()))
                return true;
        return false;
    }

    public String getDeleteQuery() {
        return "DROP TABLE IF EXISTS "+name;
    }

}
