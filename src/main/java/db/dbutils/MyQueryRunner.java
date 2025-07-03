package db.dbutils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MyQueryRunner<E> {
    public List<E> query(Connection connection, String sql, MyBeanListHandler<E> beanListHandler) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        return beanListHandler.handle(resultSet);

    }
}
