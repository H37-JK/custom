package com.hjk.custom.log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hjk.custom.model.LogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "aspect.logging.enabled", havingValue = "true", matchIfMissing = false)
public class LogAspect {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Around("@within(org.springframework.stereotype.Controller)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        var start = Instant.now();

        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String && "startDate".equals(args[i])) {
                args[i] = "2000-01-01 00:00:00";
            }
        }

        var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        var request = attributes.getRequest();
        var result = joinPoint.proceed(); /// 응답 결과
        var signature = joinPoint.getSignature();
        var methodSignature = (MethodSignature) signature;
        var method = methodSignature.getMethod();
        var end = Instant.now();
        var duration = Duration.between(start, end);


        var dto = new LogDto();

        dto.setUserName(request.getRemoteUser());
        dto.setSystCd("CMS");
        dto.setRqstUrl(request.getRequestURI()); /// 요청 URL
        dto.setHttpMthdCd(request.getMethod()); /// 요청 메서드(POST)
        dto.setRqstParams(gson.toJson(getParameter(method, joinPoint.getArgs()))); /// 요청 파라미터
        dto.setHttpRespStr(gson.toJson(result)); /// 응답 결과(헤더, 바디, 상태코드 포함)
        dto.setExecEllapTimeMs(duration.toMillis()); /// 소요 시간

        log.info("result = {}", gson.toJson(dto));
        return result;
    }

    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            ///  RequestBody 어노테이션 붙은 곳에서 파라미터 추출
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }

            ///  RequestParam 어노테이션 붙은 곳에서 파라미터 추출
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!requestParam.value().isEmpty()) {
                    key = requestParam.value();
                }
                if (args[i] != null) {
                    map.put(key, args[i]);
                    argList.add(map);
                }
            }
        }
        if (argList.isEmpty()) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }
}

