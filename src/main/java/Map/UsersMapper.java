package Map;
import Model.Users;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersMapper implements RowMapper{
    public Users map(ResultSet rs)throws SQLException  {
        return new Users(rs.getInt("user_id"),rs.getString("firstname"),rs.getString("lastname"),
                rs.getString("national_code"),rs.getString("username"),rs.getString("password"),
                rs.getBoolean("is_active"));

    }
}
