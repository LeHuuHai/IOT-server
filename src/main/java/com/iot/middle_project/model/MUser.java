package com.iot.middle_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document(collection = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MUser implements UserDetails {
    @Id
    @Field("_id")
    private ObjectId id;
    @Indexed(unique = true)
    private String username;
    private String password;
    @DBRef
    private List<Device> devices;
    @DBRef
    private List<RefreshToken> refreshTokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public boolean isContainRefreshToken(RefreshToken refreshToken){
        return this.getRefreshTokens().contains(refreshToken);
    }
}
