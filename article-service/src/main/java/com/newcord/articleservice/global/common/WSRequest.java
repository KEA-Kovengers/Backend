package com.newcord.articleservice.global.common;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WSRequest<T> implements Serializable {
    private final String uuid;
    private final Long userID;
    private final T dto;
}
