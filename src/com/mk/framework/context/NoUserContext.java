package com.mk.framework.context;


/**
 * Created with IntelliJ IDEA.
 * User: zhangyu
 * Date: 12-8-6
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public class NoUserContext extends UserContext {
    /*public Collection<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authSet = Sets.newHashSet();
        authSet.add(new GrantedAuthorityImpl("ROLE_USER"));
        return authSet;
    }*/

    public String getPassword() {
        return "getPassword";
    }

    public String getUsername() {
        return "getUsername";
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

    public boolean isEnabled() {
        return true;
    }
}
