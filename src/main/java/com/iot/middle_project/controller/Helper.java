package com.iot.middle_project.controller;

import com.iot.middle_project.dto.SoilMoisturePostDTO;
import com.iot.middle_project.model.Device;
import com.iot.middle_project.model.MUser;
import com.iot.middle_project.model.SoilMoistureData;
import com.iot.middle_project.service.DeviceService;
import com.iot.middle_project.service.MUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

@Component
public class Helper {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MUserDetailService mUserDetailService;

    public static PublicKey getPublicKeyFromBase64(String base64PublicKey) throws Exception {
        // Giải mã Base64 để lấy mảng byte
        if (base64PublicKey.length() % 4 != 0) {
            // Thêm padding "=" vào cuối chuỗi nếu cần thiết
            base64PublicKey += "=".repeat(4 - base64PublicKey.length() % 4);
        }
        byte[] decodedKey = Base64.getDecoder().decode(base64PublicKey);

        // Chuyển mảng byte thành PublicKey sử dụng KeyFactory
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public boolean verifySoilMoistureDTO(SoilMoisturePostDTO soilMoisturePostDTO) throws Exception {
        // Lấy public key của thiết bị từ DeviceRepository
        String publicKeyBase64 = deviceService.findByDeviceId(soilMoisturePostDTO.getDeviceId()).getPublicKey();
        PublicKey publicKey = getPublicKeyFromBase64(publicKeyBase64);

        // Chữ ký cần xác minh (từ DTO)
        String signature = soilMoisturePostDTO.getSignature();

        // Dữ liệu cần xác minh chữ ký
        String dataToVerify = soilMoisturePostDTO.getDeviceId() +
                soilMoisturePostDTO.getTime().toString() +
                soilMoisturePostDTO.getSoilMoistureValue();

        // Chuyển chữ ký từ Base64 string về byte[]
        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        // Sử dụng RSA hoặc thuật toán mã hóa bất đối xứng để xác minh chữ ký
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);

        // Cung cấp dữ liệu cần kiểm tra
        sig.update(dataToVerify.getBytes(StandardCharsets.UTF_8));

        // Kiểm tra chữ ký
        return sig.verify(signatureBytes);
    }

    public MUser validateUser(){
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MUser mUser = mUserDetailService.loadUserByUsername(auth.getName());
        if(mUser!=null)
            return mUser;
        return null;
    }

    public Device validateDevice(String deviceId){
        MUser user = validateUser();
        if(user==null)
            return null;
        Device device = deviceService.findByDeviceId(deviceId);
        if(user.getDevices().contains(device)){
            return device;
        }
        return null;
    }
}
