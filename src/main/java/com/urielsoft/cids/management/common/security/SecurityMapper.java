package com.urielsoft.cids.management.common.security;

import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-06
 */
@Repository
@Db1Mapper
public interface SecurityMapper {

	/**
	 * Select User Info By UserId
	 *
	 * @param userId UserId is username of spring security
	 * @return
	 * @throws DataAccessException
	 */
	LoginUserDetails selectUserInfoByUserId(@Param("userId") String userId) throws DataAccessException;
}
