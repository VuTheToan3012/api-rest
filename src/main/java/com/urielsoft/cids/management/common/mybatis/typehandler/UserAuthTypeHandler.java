package com.urielsoft.cids.management.common.mybatis.typehandler;

import com.urielsoft.cids.management.biz.common.enums.UserAuthTypeEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-05
 */
@MappedTypes(UserAuthTypeEnum.class)
public class UserAuthTypeHandler implements TypeHandler<UserAuthTypeEnum> {

	@Override
	public void setParameter(PreparedStatement ps, int i, UserAuthTypeEnum parameter, JdbcType jdbcType) throws SQLException {
		
	}

	@Override
	public UserAuthTypeEnum getResult(ResultSet rs, String columnName) throws SQLException {
		return null;
	}

	@Override
	public UserAuthTypeEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public UserAuthTypeEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
	}
}
