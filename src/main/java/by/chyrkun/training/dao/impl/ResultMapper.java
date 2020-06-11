package by.chyrkun.training.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultMapper<T> {
    T convert(ResultSet resultSet) throws SQLException;
}
