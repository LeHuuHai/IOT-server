package com.iot.middle_project.service;

import com.iot.middle_project.model.MUser;
import com.iot.middle_project.repository.MUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MUserDetailService implements UserDetailsService {

    @Autowired
    private MUserRepository mUserRepository;

    @Override
    public MUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return mUserRepository.findByUsername(username);
    }

    public MUser save(MUser mUser){
        return mUserRepository.save(mUser);
    }

}
