package ru.otus.pyltsin.HW10.DAO.MyExecutor;

import ru.otus.pyltsin.HW10.DAO.MyExecutor.Helper.ConnectionHelper;
import ru.otus.pyltsin.HW10.DAO.MyExecutor.Helper.ReflectionHelper;
import ru.otus.pyltsin.HW10.common.DataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
@SuppressWarnings("Duplicates")
public class MyExecutorUser implements ExecutorUser {
    @Override
    public <T extends DataSet> void save(T dataSet) {
        Class clazz = dataSet.getClass();
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new UnsupportedOperationException();
        }

        String nameTable = getNameTable(clazz);

        HashMap<String, Object> map = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                continue;
            }

            Class fieldType = field.getType();
            if (ReflectionHelper.isWrapperOrPrimitivesType(fieldType)) {
                try {
                    if (field.isAnnotationPresent(Column.class)) {
                        boolean flag = field.isAccessible();
                        field.setAccessible(true);
                        map.put(getName(field), field.get(dataSet));
                        field.setAccessible(flag);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        storeInDB(nameTable, map);
    }

    private String getNameTable(Class clazz) {
        String nameTable = clazz.getName();
        if (clazz.isAnnotationPresent(Table.class)) {
            nameTable = ((Table) clazz.getAnnotation(Table.class)).name();
        }
        return nameTable;
    }

    private void storeInDB(String nameTable, HashMap<String, Object> map) {
        Connection connection = ConnectionHelper.getConnection();

        try {
            connection.setAutoCommit(false);

            String insert = "insert into " + nameTable;
            StringBuilder column = new StringBuilder("(");
            StringBuilder values = new StringBuilder(" values (");
            for (String s : map.keySet()) {
                column.append(s).append(",");
                values.append(getValues(map.get(s))).append(",");
            }
            column.deleteCharAt(column.length() - 1);
            values.deleteCharAt(values.length() - 1);

            column.append(")");
            values.append(")");

            String sql = insert + column.toString() + values.toString();
            try (Statement s = connection.createStatement()) {
                s.execute(sql);

                connection.commit();
            }
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private String getValues(Object o) {
        if (ReflectionHelper.isString(o.getClass())) {
            return "'" + o + "'";
        } else {
            return o.toString();
        }
    }

    private String getName(Field field) {
        String name = field.getName();
        if (field.isAnnotationPresent(Column.class)) {
            name = field.getAnnotation(Column.class).name();
        }
        return name;
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {

        String nameSql = " from " + getNameTable(clazz) + " where id=" + id;
        StringBuilder columns = new StringBuilder("select ");

        for (Field field : clazz.getDeclaredFields()) {
            columns.append(getName(field)).append(",");
        }

        columns.deleteCharAt(columns.length() - 1);
        String sql = columns.toString() + nameSql;

        try {
            return readFromDB(sql, clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T extends DataSet> T findByParam(String param, String name, Class<T> clazz) {
        long id = getIdByParam(param, name, clazz);
        return load(id, clazz);
    }

    private <T extends DataSet> long getIdByParam(String param, String name, Class<T> clazz) {
        Connection connection = ConnectionHelper.getConnection();
        String sql = "select id from " + getNameTable(clazz) + " where " + param + " =" + "'" + name + "'" + " limit 1";


        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            if (resultSet != null) {
                return (long) resultSet.getObject(1);
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private <T extends DataSet> T readFromDB(String sql, Class<T> clazz) throws SQLException {
        T userOut;
        Connection connection = ConnectionHelper.getConnection();
        try (Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(sql);

            userOut = buildObject(rs, clazz);
        }
        connection.close();

        return userOut;
    }

    @org.jetbrains.annotations.Nullable
    private <T extends DataSet> T buildObject(ResultSet rs, Class<T> clazz) {
        try {

            boolean flagNotNull = rs.next();
            if (!flagNotNull) {
                return null;
            }

            Object obj = clazz.newInstance();

            for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
                Field field = clazz.getDeclaredFields()[i];
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object val = rs.getObject(i + 1);
                field.set(obj, val);
                field.setAccessible(flag);
            }
            return (T) obj;

        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
