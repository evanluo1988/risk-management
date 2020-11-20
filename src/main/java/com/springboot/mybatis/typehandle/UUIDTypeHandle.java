package com.springboot.mybatis.typehandle;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by zx on 2020/7/25.
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(value = UUID.class)
public class UUIDTypeHandle extends BaseTypeHandler<UUID> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, UUID uuid, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, uuid.toString());
    }

    @Override
    public UUID getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getString(s) != null ? UUID.fromString(resultSet.getString(s)): null;
    }

    @Override
    public UUID getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString(i) != null ? UUID.fromString(resultSet.getString(i)): null;
    }

    @Override
    public UUID getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getString(i) != null ? UUID.fromString(callableStatement.getString(i)): null;
    }
}
