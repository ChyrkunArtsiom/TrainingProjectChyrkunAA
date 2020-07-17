package by.chyrkun.training.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultMapper<T> {
    T convert(ResultSet resultSet) throws SQLException;
}
