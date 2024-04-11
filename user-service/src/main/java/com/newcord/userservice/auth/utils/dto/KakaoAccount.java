package com.newcord.userservice.auth.utils.dto;

import lombok.Data;

@Data
public class KakaoAccount {
    private Boolean profile_nickname_needs_agreement;
    private Boolean profile_image_needs_agreement;
    private KakaoProfile profile;
}
