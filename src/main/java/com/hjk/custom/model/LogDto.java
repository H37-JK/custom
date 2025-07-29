package com.hjk.custom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogDto {

    private String userName;

    private String systCd;

    private Long membNo;

    private String clntIp;

    private String httpMthdCd;

    private String rqstUrl;

    private String rqstParams;

    private Long execEllapTimeMs;

    private String httpRespStr;
}
