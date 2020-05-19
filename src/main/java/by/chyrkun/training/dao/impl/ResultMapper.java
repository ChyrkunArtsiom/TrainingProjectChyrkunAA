package by.chyrkun.training.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultMapper<T> {
    List<T> convert(ResultSet resultSet) throws SQLException;
}
