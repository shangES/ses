package com.mk.framework.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.google.common.collect.Sets;
import com.mk.framework.constance.Constance;
import com.mk.system.entity.Function;
import com.mk.system.entity.User;

public class UserContext implements UserDetails {
	private static final long serialVersionUID = 1L;
	private String companyid;
	private String companyname;
	private String deptid;
	private String deptname;
	private String postname;
	private String jobname;
	private String quotaname;
	private String mobile;

	private String userId;
	private String loginname;
	private String password;
	private String username;
	private boolean admin;
	private Map<String, List<String>> buttonMap;

	public UserContext() {

	}

	public UserContext(User user) {
		loginname = user.getLoginname();
		password = user.getPassword();
		userId = user.getUserguid();
		// username = user.getUsername();
		admin = user.getIsadmin().equals(Constance.isAdmin1);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getQuotaname() {
		return quotaname;
	}

	public void setQuotaname(String quotaname) {
		this.quotaname = quotaname;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public String getUserId() {
		return userId;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getLoginname() {
		return loginname;
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authSet = Sets.newHashSet();
		authSet.add(new GrantedAuthorityImpl("ROLE_NORMAL"));
		authSet.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		return authSet;

	}

	public Map<String, List<String>> getButtonMap() {
		return buttonMap;
	}

	public void setButtonMap(Map<String, List<String>> buttonMap) {
		this.buttonMap = buttonMap;
	}

	/**
	 * 从用户菜单加载权限 ，初始化权限列表
	 * 
	 * @param systemMenu
	 */
	public void initUserOpration(List<Function> funs) {
		buttonMap = new HashMap<String, List<String>>();
		Map<String, Function> temMap = new HashMap<String, Function>();
		for (Function fun : funs) {
			temMap.put(fun.getFunid(), fun);
		}
		for (Function fun : funs) {
			// 如果是按钮
			if (fun.getFuntype().equals(Constance.funTypeButton) && temMap.get(fun.getFunpid()) != null) {
				String key = temMap.get(fun.getFunpid()).getJavaevent();
				if (buttonMap.get(trimUri(key)) != null) {
					buttonMap.get(trimUri(key)).add(fun.getJavaevent());
				} else {
					List<String> buttonList = new ArrayList<String>();
					buttonList.add(fun.getJavaevent());
					buttonMap.put(trimUri(key), buttonList);
				}
			}
		}
	}

	/**
	 * 将参数去除？后面的串
	 * 
	 * @param keyUrl
	 * @return
	 */
	private String trimUri(String keyUrl) {
		if (StringUtils.hasLength(keyUrl) && keyUrl.contains("?")) {
			return keyUrl.substring(0, keyUrl.indexOf("?"));
		}
		return keyUrl;
	}

	public List<String> accessableOperation(String functionUri) {
		if (buttonMap == null) {
			return null;
		}
		return buttonMap.get(functionUri);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserContext that = (UserContext) o;

		if (loginname != null ? !loginname.equals(that.loginname) : that.loginname != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return loginname != null ? loginname.hashCode() : 0;
	}

}
