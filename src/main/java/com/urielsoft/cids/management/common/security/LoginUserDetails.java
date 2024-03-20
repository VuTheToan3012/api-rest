package com.urielsoft.cids.management.common.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-06
 */
@Slf4j
public class LoginUserDetails implements UserDetails {

	private static final long serialVersionUID = 6088431173505229297L;

	/**
	 * User id
	 */
	@Getter
	@Setter
	private String userId;

	/**
	 * User Name
	 */
	@Getter
	@Setter
	private String username;

	/**
	 * User Password
	 */
	@Getter
	@Setter
	private String password;

	/**
	 * User Authenticate Type Name
	 */
	@Getter
	@Setter
	private String authTyNm;

	/**
	 * User IP Address
	 */
	@Getter
	@Setter
	private String userLoginIp;

	@Getter
	private List<GrantedAuthority> authorities;

	/**
	 * Only call by MyBatis Mapper
	 * userAuthType to authorities
	 *
	 * @param userAuthType
	 */
	public void setUserAuthType(String userAuthType) {
		if (StringUtils.isNotBlank(userAuthType)) {
			this.authorities = Collections.unmodifiableList(
					Arrays.stream(userAuthType.split(","))
							.map(e -> {
								return new SimpleGrantedAuthority("ROLE_" + e.toUpperCase());
							})
							.collect(Collectors.toList())
			);
		} else {
			this.authorities = new ArrayList<>();
		}
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO : Depending on the project, implementation may be required
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO : Depending on the project, implementation may be required
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO : Depending on the project, implementation may be required
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO : Depending on the project, implementation may be required
		return true;
	}
}
