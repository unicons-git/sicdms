package com.g3way.sicims.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Package Name	: com.g3way.sicims.annotation
 * @Class Name		: RSAAspect.java
 * @Description		: RES 키 생성 오노테이션
 * @Modification	:
 * @-----------------------------------------------------------------
 * @ Date			| Name			| Comment
 * @-------------  -----------------   ------------------------------
 * @ 2021. 2. 22.		| JunHee		| 최초생성
 * @-----------------------------------------------------------------
 * @Author			: JunHee
 * @Since			: 2021. 2. 22. 오후 2:03:19
 * @Version			: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RSAKeyGeneral{
}


