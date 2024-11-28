package com.test.bidon.config.security;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.test.bidon.entity.UserEntity;
import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
    
    private static final long serialVersionUID = 1L;
    
    private final UserEntity userEntity;
    
    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getUserRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }
    
    //추가한 메서드 > 마이페이지에서 사용
    public String getName() {
    	return userEntity.getName();
    }
    
    public String getNational() {
    	return userEntity.getNational();
    }
    
    public LocalDate getBirth() {
		return userEntity.getBirth();
	}
    
	public String getTel() {
		return userEntity.getTel();
	}
	
	public LocalDate getCreateDate() {
		return userEntity.getCreateDate();
	}

	public String getProfile() {
		return userEntity.getProfile();
	}
    
    //추가한 메서드 끝
    
    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userEntity.getStatus() == 0;
    }

	public Long getId() {
		return userEntity.getId();
	}


}