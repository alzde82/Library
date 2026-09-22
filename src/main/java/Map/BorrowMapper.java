package Map;
import Model.Borrow;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowMapper implements RowMapper {
   public Borrow map(ResultSet rs) throws SQLException{
        return new Borrow(rs.getInt("user_id"),rs.getString("username"),rs.getInt("book_id"),
                rs.getString("book_name"),(rs.getDate("borrowed_date")).toLocalDate());
    }
}
