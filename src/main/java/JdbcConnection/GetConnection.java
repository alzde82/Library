package JdbcConnection;
import java.sql.*;

import Exceptions.CloseConnectionFailed;
import Exceptions.ConnectionFailed;
public class GetConnection {

    public Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
        }catch(SQLException e){
            throw new ConnectionFailed("DatabaseConnectionFailed");
        }


        }
        public void closeConnection(PreparedStatement pstmnt){
            try{
                pstmnt.close();
            }catch (SQLException e){
                throw new CloseConnectionFailed("StatementsIdle");
            }
        }
    public void closeConnection(PreparedStatement pstmnt , ResultSet rs){
            try{
                pstmnt.close();
                rs.close();
            }catch(SQLException e){
                throw new CloseConnectionFailed("StatementsIdle");
            }
    }


}
