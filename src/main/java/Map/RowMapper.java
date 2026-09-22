package Map;

import Model.InterfaceModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T extends InterfaceModel> {
    T map(ResultSet rs) throws SQLException;
}
