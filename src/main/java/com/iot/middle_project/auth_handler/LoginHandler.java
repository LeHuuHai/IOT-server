package com.iot.middle_project.auth_handler;

import com.iot.middle_project.dto.TokensDTO;
import com.iot.middle_project.dto.UsernamePasswordDTO;
import com.iot.middle_project.jwt.JWTService;
import com.iot.middle_project.model.MUser;
import com.iot.middle_project.model.RefreshToken;
import com.iot.middle_project.service.MUserDetailService;
import com.iot.middle_project.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private MUserDetailService mUserDetailService;

    @PostMapping("/auth/login-basic")
    public ResponseEntity<Object> loginBasic(@RequestBody UsernamePasswordDTO usernamePasswordDTO){
        String username = usernamePasswordDTO.getUsername();
        String password = usernamePasswordDTO.getPassword();
        System.out.println(username);
        System.out.println(password);

        Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password));
        if(authentication.isAuthenticated()){
            String jwtToken = jwtService.generateToken(username);
            String refreshToken = jwtService.generateRefreshToken(username);

            MUser mUser = (MUser) mUserDetailService.loadUserByUsername(username);
            RefreshToken token = refreshTokenService.save(refreshToken);
            mUser.getRefreshTokens().add(token);
            mUserDetailService.save(mUser);

            TokensDTO tokensDTO = TokensDTO.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
            return new ResponseEntity<>(tokensDTO, HttpStatusCode.valueOf(200));
        }else{
            return new ResponseEntity<>("Invalid username or password", HttpStatusCode.valueOf(401));
        }
    }

    @GetMapping("/auth/refresh-token")
    public ResponseEntity<Object> refreshToken(HttpServletRequest request, HttpServletResponse response){
        String token = refreshTokenService.refreshToken(request, response);
        if(token==null)
            return new ResponseEntity<>("Refresh token is no longer exist or invalid", HttpStatusCode.valueOf(401));

        return new ResponseEntity<>(TokensDTO.builder()
                .accessToken(token)
                .build(), HttpStatusCode.valueOf(200));
    }
}
