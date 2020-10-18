package ru.fiz.retry.r_client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RHttplClientResponseModel {

    private int code;
    private String body;

}
