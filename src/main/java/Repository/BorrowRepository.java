package Repository;
import Exceptions.SelectException;
import Exceptions.UpdatingException;
import Map.RowMapper;
import Model.Borrow;
import java.lang.reflect.Field;

import Model.InterfaceModel;
import Validation.SqlValidation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import JdbcConnection.*;
public class BorrowRepository implements InterfaceRepository<Borrow> {
    private SqlValidation v= new SqlValidation();
    private GetConnection getConnection=new GetConnection();
    private Connection connection= getConnection.getConnection();



    public int update(Borrow borrow , String columnName,Object newValue) {
        String update = "UPDATE " + borrow.getClass().getSimpleName() + " SET "+ v.camelcaseToSnakeCase(columnName)+" =? ";
        for (Field f : borrow.getClass().getDeclaredFields()) {
            if (f.getName().equals("userId")) {

                update+="WHERE "+v.camelcaseToSnakeCase(f.getName()) +"= ? ";
                continue;
            }
            if(f.getName().equals("bookId"))
                update +="AND " +v.camelcaseToSnakeCase(f.getName()) +"= ? ";

        }
        try {
            PreparedStatement pstmnt = connection.prepareStatement(update);

            pstmnt.setObject(1,v.convertTypes(newValue));
            pstmnt.setInt(2,borrow.getUserId());
            pstmnt.setInt(3,borrow.getUserId());
            return pstmnt.executeUpdate();
        }catch(SQLException e){
                throw new UpdatingException("FailedToUpdate " + borrow.getClass().getSimpleName() + " table");
        }
    }

}
