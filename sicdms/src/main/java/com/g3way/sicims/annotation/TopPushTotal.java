package com.g3way.sicims.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Package Name	: com.g3way.sicims.annotation
 * @Class Name		: TopPush.java
 * @Description		: 상단 알림 건수 조회 로직 오노테이션
 * @Modification	:
 * @-----------------------------------------------------------------
 * @ Date			| Name			| Comment
 * @-------------  -----------------   ------------------------------
 * @ 2021. 6. 29.		| JunHee		| 최초생성
 * @-----------------------------------------------------------------
 * @Author			: JunHee
 * @Since			: 2021. 6. 29. 오후 2:03:19
 * @Version			: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TopPushTotal{
}


