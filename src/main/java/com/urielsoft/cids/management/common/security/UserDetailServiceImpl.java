package com.urielsoft.cids.management.common.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-06
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private final SecurityMapper securityMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUserDetails loginUserDetails = this.securityMapper.selectUserInfoByUserId(username);
		loginUserDetails.setUserAuthType(loginUserDetails.getAuthTyNm());
		// TODO : If you need to collect additional information before authentication, implement it here
		
		return loginUserDetails;
	}
}
