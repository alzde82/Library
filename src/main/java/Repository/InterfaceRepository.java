package Repository;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import Exceptions.DeleteException;
import Exceptions.InsertionException;
import Exceptions.SelectException;
import Exceptions.UpdatingException;
import JdbcConnection.GetConnection;
import Map.RowMapper;
import Model.InterfaceModel;
import java.sql.*;
import ORM.InsertORM;
import Validation.SqlValidation;

public interface InterfaceRepository<T extends InterfaceModel> {
    SqlValidation v = new SqlValidation();
    GetConnection connection=new GetConnection();
    InsertORM<InterfaceModel> orm= new InsertORM<>();

    default int create(T model){
        String create= orm.create(model);
        try(Statement stmnt=connection.getConnection().createStatement()){
            return stmnt.executeUpdate(create);
        }catch(SQLException e){
            System.out.println("FailedToCreate " + model.getClass().getSimpleName() +" Table");
            return -1;

        }


    }
    default int insert(T model){
        String insert =orm.insert(model);
        try{
            Statement stmnt=connection.getConnection().createStatement();
            return stmnt.executeUpdate(insert);

        }catch (SQLException e){
            throw new InsertionException("FailedToInsertInto" + model.getClass().getSimpleName() +" Table");
        }
    }
    default List<T> readBy(T model,String columnName,RowMapper<T> mapper)throws NullPointerException {
        List<T> arrayList=new ArrayList<>();
        String select =orm.selectBy(model,columnName);
            for ( Field f : model.getClass().getDeclaredFields()){
                f.setAccessible(true);
                if(f.getName().equals(columnName)) {
                   try {
                       if (f.get(model) == null)
                           return readByNull(model,columnName,mapper);
                   }catch(IllegalAccessException e){
                       throw new SelectException("FailedToSelectFrom " + model.getClass().getSimpleName() + " Table");

                   }

                    try (PreparedStatement pstmnt = connection.getConnection().prepareStatement(select)) {
                        pstmnt.setObject(1, v.convertTypes(f.get(model)));
                        try(ResultSet rs =pstmnt.executeQuery()){
                            while(rs.next()){
                                arrayList.add(mapper.map(rs));
                            }
                        }
                    } catch (SQLException | IllegalAccessException e) {
                        throw new SelectException("FailedToSelectFrom " + model.getClass().getSimpleName() + " Table");
                    }
                }
            }
        return arrayList;
    }
     default List<T> readByNull(T model, String nullColumnName, RowMapper<T> mapper){
        List<T> arrayList=new ArrayList<>();
        String selectNull=orm.selectbyNull(model,nullColumnName);
        try (PreparedStatement pstmnt=connection.getConnection().prepareStatement(selectNull);
        ResultSet rs = pstmnt.executeQuery()){
                    while(rs.next()){
                        arrayList.add(mapper.map(rs));
                    }
                    return arrayList;
        }catch(SQLException e){
            throw new SelectException("FailedToSelectFrom " + model.getClass().getSimpleName() + " Table");

        }
    }

    default List<T> readAll(T model,RowMapper<T> mapper){

        List<T> list=new ArrayList<>();
        final String select=orm.selectAll(model);
        try(PreparedStatement pstmnt= connection.getConnection().prepareStatement(select);
            ResultSet result=pstmnt.executeQuery();){
                while(result.next()){
                    list.add(mapper.map(result));
                }
        }catch(SQLException e){
            throw new SelectException("FailedToSelectAll " + model.getClass().getSimpleName() + " Table");
        }
        return list;
    }

    default public int update(T model , String columnName , Object newValue){
        String update= orm.update(model, columnName);
        try{
            PreparedStatement pstmnt= connection.getConnection().prepareStatement(update);
            pstmnt.setObject(1,v.convertTypes(newValue));
            return pstmnt.executeUpdate();
        }catch(SQLException e){
                throw new UpdatingException("FailedToUpdate " + model.getClass().getSimpleName() + " table");
        }

    }

    default int delete(T model){
        String delete= orm.delete(model);
        try(Statement stmnt=connection.getConnection().createStatement();){
            return stmnt.executeUpdate(delete);
        }catch(SQLException e){
            throw new DeleteException("FailedToDeleteRowFrom " + model.getClass().getSimpleName() + " table");
        }


    }

}
