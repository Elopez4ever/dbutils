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

            try (Connection connection = dataSource.getConnection()) {
                MyQueryRunner runner = new MyQueryRunner();
                MyBeanListHandler<User> handler = new MyBeanListHandler<>(User.class);

                System.out.println("\n**************** 查询 ******************\n");
                List<User> query = runner.query(connection, "select * from user", handler);
                for (User user : query) {
                    System.out.println(user);
                }

                System.out.println("\n************** 带参数的查询 **************\n");
                List<User> queryWithParams = runner.query(connection, "select * from user where id = ? and name = ?", handler, 1, "张三");
                for (User u : queryWithParams) {
                    System.out.println(u);
                }

                System.out.println("\n**************** 插入 ******************\n");
                int i = runner.executeUpdate(connection, "insert into user(name, password) values (?, ?)", "1", "1111");
                System.out.println("成功插入了 " + i + " 条数据");

                System.out.println("\n**************** 删除 ******************\n");
                i = runner.executeUpdate(connection, "delete from user where name = ?", "1");
                System.out.println("成功删除了 " + i + " 条数据");

                System.out.println("\n**************** 更新 ******************\n");
                i = runner.executeUpdate(connection, "update user set password = ? WHERE name = ?", "更新成功", "张三");
                System.out.println("成功更新了 " + i + " 条数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
