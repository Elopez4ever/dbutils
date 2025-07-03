package db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import db.dbutils.MyBeanListHandler;
import db.dbutils.MyQueryRunner;
import db.entity.User;

import java.sql.Connection;
import java.util.List;


public class Test {
    public static void main(String[] args) {
        try (ComboPooledDataSource dataSource = new ComboPooledDataSource()) {

            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ds");
            dataSource.setUser("root");
            dataSource.setPassword("123456");

            dataSource.setInitialPoolSize(20);
            dataSource.setMaxPoolSize(40);
            dataSource.setMinPoolSize(2);
            dataSource.setAcquireIncrement(2);

            Connection connection = dataSource.getConnection();
            String sql = "select * from user";
            MyQueryRunner<User> userMyQueryRunner = new MyQueryRunner<>();
            List<User> query = userMyQueryRunner.query(connection, sql, new MyBeanListHandler<>(User.class));
            for (User user : query) {
                System.out.println(user);
            }
        } catch (Exception e) {
            System.out.println("wrong");
        }
    }
}
