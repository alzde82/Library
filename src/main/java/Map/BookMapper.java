package Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Book;
public class BookMapper implements RowMapper{
    public Book map(ResultSet rs) throws SQLException{
            return new Book( rs.getInt("book_id"),rs.getString("name"),rs.getString("author_name"),
                    rs.getDouble("price"),rs.getInt("quantity"),rs.getBoolean("is_available"));


    }
}
