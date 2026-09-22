package ORM;
import Validation.SqlValidation;
import Exceptions.InvalidValueException;
import Model.InterfaceModel;
import java.lang.reflect.*;
import java.lang.StringBuilder;
import java.sql.PreparedStatement;
import java.time.LocalDate;

import Model.*;
public class InsertORM<T extends InterfaceModel> {
    SqlValidation v =new SqlValidation();
//                    -->SQLQueryForCreate
            public String create(T model){
                String create="CREATE TABLE IF NOT EXISTS "+ model.getClass().getSimpleName()+"(\n";
                create+=initializeFields(model) +")";
                return create;
            }

    public String selectAll(T model){
                String select = "SELECT * FROM " + model.getClass().getSimpleName();
                return select;
    }
    public String select(T model,String columns){
                StringBuilder select = new StringBuilder("SELECT ");
                for (Field f:model.getClass().getDeclaredFields()){
                    if(f.getName().equals(columns))
                       return select.append(f.getName()).append(" FROM ").append(model.getClass().getSimpleName()).toString();
                }
                return "NoSuchColumnFounded";

    }
    public String selectBy(T model,String columnName){
        return "SELECT * FROM " + model.getClass().getSimpleName() +" WHERE " +v.camelcaseToSnakeCase(columnName) + " = ?";

    }
    public String selectbyNull(T model,String columnName){
        return "SELECT * FROM " + model.getClass().getSimpleName() +" WHERE " +v.camelcaseToSnakeCase(columnName) + " IS NULL";

    }
//                    -->SQLQueryForRead
    public String insert(T model) {
        String insert = "INSERT INTO " + model.getClass().getSimpleName() + "(";
        insert += getFields(model) + ") ";
        insert += "VALUES(" + getValues(model) + ")";
        return insert;

    }

//                    -->SQLQueryForDelete
    public String delete(T model){
                String delete= "DELETE FROM "+ model.getClass().getSimpleName() +" WHERE ";
              try{
                  Field [] fields= model.getClass().getDeclaredFields();
                  fields[0].setAccessible(true);
                  delete+=v.camelcaseToSnakeCase(fields[0].getName()) + "=" +fields[0].get(model);
                  return delete;

              }catch (IllegalAccessException e){
                  return "FailedToFindValues";
              }
    }
    public String update(T model, String columnName){
                String fixedColumn=v.camelcaseToSnakeCase(columnName);
            String update = "UPDATE " + model.getClass().getSimpleName() + " SET "+fixedColumn+" = ? WHERE ";
                        try {
                Field[] fields = model.getClass().getDeclaredFields();

                fields[0].setAccessible(true);
                update+=v.camelcaseToSnakeCase(fields[0].getName()) +" = " + fields[0].get(model);
                return update;
            }catch(IllegalAccessException e){
                throw new InvalidValueException("ValueNotFoundOrFieldDoesntExists");
            }

    }

//                    -->GetFields
    private String getFields(T model) {
                String fixedField;
        StringBuilder vessel = new StringBuilder();
        Field[] fields = model.getClass().getDeclaredFields();
        for (Field f : fields) {
            if(model instanceof Book ||model instanceof Users){
            if(f==fields[0])
                continue;
            }

            f.setAccessible(true);
            fixedField=v.camelcaseToSnakeCase(f.getName());
            vessel.append(fixedField).append(" , \n");
        }
        vessel.setLength(vessel.length() - 3);
        return vessel.toString();

    }
//                    -->GetValuesOfFields"(ToInsertLocalDateToSQLDateUMustPutItInto('')ThingsThenSendItToDB)"
    private String getValues(T model) {
        StringBuilder vessel = new StringBuilder();
        String convertion;
        Field[] fields = model.getClass().getDeclaredFields();
        try {
            for (Field f : fields) {
                if(model instanceof Book ||model instanceof Users){
                    if(f==fields[0])
                        continue;
                }
                f.setAccessible(true);
                if(f.get(model)instanceof LocalDate){
                    convertion=v.convertToSqlDate((LocalDate) f.get(model));
                    vessel.append(convertion).append(" , ");
                    continue;
                }
                if (f.get(model) instanceof String) {
                    convertion=v.convertToVarchar((String)f.get(model));
                    vessel.append(convertion).append(" , ");
                }else vessel.append(f.get(model)).append(" , ");
            }
        } catch (IllegalAccessException e) {
            throw new InvalidValueException("ValueNotFoundOrFieldDoesntExists");

        }
        vessel.setLength(vessel.length() - 2);
        return vessel.toString();
    }
//                    -->InitializeSqlColumnNameAndTypes
    private String initializeFields(T model){
                StringBuilder vessel= new StringBuilder();
                String fixedName;
                Field[] fields= model.getClass().getDeclaredFields();
        for(Field f:fields){
            f.setAccessible(true);
            fixedName =v.camelcaseToSnakeCase(f.getName());
            if(f==fields[0]){
                        vessel.append(fixedName).append(" SERIAL UNIQUE,\n");
                        continue;
                    }
                    vessel.append(fixedName).append(" ").append(v.convertToSqlType(f.getType().getSimpleName())).append(" , \n");
                }
            vessel.setLength(vessel.length()-3);
        return vessel.toString();
    }
}






