package com.g3way.sicims.exception;

/**
 * @Package Name	: com.g3way.sicims.exception
 * @Class Name		: SicimsException.java
 * @Description		: sicims 에서 사용하는 커스텀 예외
 * @Modification	:
 * @-----------------------------------------------------------------
 * @ Date			| Name			| Comment
 * @-------------  -----------------   ------------------------------
 * @ 2021. 2. 3.	| JunHee		| 최초생성
 * @-----------------------------------------------------------------
 * @Author			: JunHee
 * @Since			: 2021. 2. 3. 오전 10:39:24
 * @Version			: 1.0
 */
public class SicimsException  extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public SicimsException(String msg) {
        super(msg);
    }

    public SicimsException(String msg, Exception e) {
        super(msg, e);
    }

}