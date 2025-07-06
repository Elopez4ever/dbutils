package db.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import db.dbutils.MyBeanListHandler;
import db.dbutils.MyQueryRunner;
import db.entity.User;

import java.sql.Connection;
import java.util.List;

public class UserService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ComboPooledDataSource dataSource = new ComboPooledDataSource();
    private static final MyQueryRunner runner = new MyQueryRunner();

    static {
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ds");
            dataSource.setUser("root");
            dataSource.setPassword("123456");
            dataSource.setInitialPoolSize(20);
            dataSource.setMaxPoolSize(40);
            dataSource.setMinPoolSize(2);
            dataSource.setAcquireIncrement(2);
        } catch (Exception e) {
            throw new RuntimeException("数据库连接池配置失败: " + e.getMessage(), e);
        }
    }

    /**
     * 查询所有用户并返回 JSON
     */
    public String findAll() {
        String sql = "select * from user";
        try (Connection conn = dataSource.getConnection()) {
            List<User> list = runner.query(conn, sql, new MyBeanListHandler<>(User.class));
            return mapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
