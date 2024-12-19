package com.iot.middle_project.service;

import com.iot.middle_project.jwt.JWTService;
import com.iot.middle_project.model.MUser;
import com.iot.middle_project.model.RefreshToken;
import com.iot.middle_project.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    MUserDetailService mUserDetailService;

    public RefreshToken getRefreshTokenByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken save(String token){
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public String refreshToken(HttpServletRequest request, HttpServletResponse response){
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }
        if(username!=null){
            MUser mUser = (MUser) mUserDetailService.loadUserByUsername(username);
            RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
            if(mUser.isContainRefreshToken(refreshToken)){
                if(jwtService.validToken(token, mUser)){
                    return jwtService.generateToken(username); // Trả về token mới với mã trạng thái 200
                }
            }
            return null;
        }
        return null;
    }
}
