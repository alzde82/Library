package Validation;
import Exceptions.ConvertionException;

import javax.xml.crypto.Data;
import java.lang.Character;
import java.sql.Date;
import java.time.LocalDate;

public class SqlValidation {
    public String convertToSqlType(){
        return null;
    }
    public String camelcaseToSnakeCase(String obj){
        StringBuilder newObj=new StringBuilder();
        for (int i = 0; i <obj.length() ; i++) {
            if(Character.isUpperCase(obj.charAt(i))){
                newObj.append("_").append(Character.toLowerCase(obj.charAt(i)));
            }else newObj.append(obj.charAt(i));
        }
        return newObj.toString();
    }
    public String convertToVarchar(String obj){
            return "'" + obj + "'";

    }
    public String convertToSqlType(String type){
        if(type.equals("String"))
            return "VARCHAR(255)";
        if(type.equals("int")||type.equals("Integer"))
            return "INTEGER";
        if(type.equals("double")||type.equals("Double") || type.equals("Float"))
            return "FLOAT";
        if(type.equals("boolean")|| type.equals("Boolean"))
            return "BOOLEAN";
        if(type.equals("LocalDate") || type.equals("Date")||type.equals("LocalDateTime"))
            return "DATE";
        else throw new ConvertionException("NoSuchTypeFound");

    }
    public String convertToSqlDate(LocalDate date){
        return "'"+date+"'";
    }
    public Object convertTypes(Object obj){
        if(obj instanceof String) {
            return (String) obj;
        }
        if(obj instanceof Integer) {
            return (Integer) obj;
        }
        if(obj instanceof Double) {
            return (Double) obj;
        }
        if(obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if(obj instanceof LocalDate) {
            return Date.valueOf((LocalDate)obj);
        }
        throw new ConvertionException("NoSuchTypeFounded:_)");
    }
}
