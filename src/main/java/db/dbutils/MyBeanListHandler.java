package db.dbutils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MyBeanListHandler<E> {

    private final Class<E> cls;

    public MyBeanListHandler(Class<E> cls) {
        this.cls = cls;
    }

    public List<E> handle(ResultSet resultSet) {
        List<E> list = new ArrayList<>();
        try {
            Constructor<E> constructor = cls.getConstructor();
            Field[] declaredFields = cls.getDeclaredFields();
            Method[] methods = cls.getMethods();

            while (resultSet.next()) {
                E e = constructor.newInstance();
                for (Field declaredField : declaredFields) {
                    String name = declaredField.getName();
                    String setter = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                    Object value = resultSet.getObject(name);

                    for (Method method : methods) {
                        if (method.getName().equals(setter) && method.getParameterCount() == 1) {
                            method.invoke(e, value);
                            break;
                        }
                    }
                }
                list.add(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}

