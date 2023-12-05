package com.g3way.sicims.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Package Name	: com.g3way.sicims.annotation
 * @Class Name		: PrvcLog.java
 * @Description		: 개인정보 접근 로그 오노테이션
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PrvcInfoLog{
}


