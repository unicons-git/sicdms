package com.g3way.sicims.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.g3way.sicims.exception.SicimsException;
import com.g3way.sicims.util.common.CommonUtil;
import com.g3way.sicims.util.common.SessionUtil;

public class RsaUtil {

	private static final Logger LOG = LoggerFactory.getLogger(RsaUtil.class);

    private static final String RSA_WEB_KEY = "_RSA_WEB_Key_"; // 개인키 session key
    private static final String RSA_INSTANCE = "RSA"; 		 // rsa transformation


	 /**
     * 복호화
     *
     * @param privateKey
     * @param securedValue
     * @return
     * @throws Exception
     */
    public static String decryptRsa(String securedValue) {
    	HttpSession session = SessionUtil.getSession();

    	Object key = session.getAttribute(RSA_WEB_KEY); // session에 RSA 개인키를 세션에 저장
    	if(key != null && key instanceof java.security.PrivateKey){
			return decryptRsa((PrivateKey)key, securedValue);
    	}
    	else
    		return null;
    }

	 /**
     * 복호화
     *
     * @param privateKey
     * @param securedValue
     * @return
     * @throws Exception
     */
    public static String decryptRsa(String securedValue, HttpSession session ) {
    	Object key = session.getAttribute(RSA_WEB_KEY); // session에 RSA 개인키를 세션에 저장
    	if(key != null && key instanceof java.security.PrivateKey){
			return decryptRsa((PrivateKey)key, securedValue);
    	}
    	else
    		return null;
    }

	 /**
     * 복호화
     *
     * @param privateKey
     * @param securedValue
     * @return
     * @throws Exception
     */
    public static String decryptRsa(PrivateKey privateKey, String securedValue) {
    	String decryptedValue = securedValue;
    	if(securedValue == null || "".equals(securedValue)){
    		return securedValue;
    	}

    	try{
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        byte[] encryptedBytes = hexToByteArray(securedValue);
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
	        decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
    	}
    	catch(InvalidKeyException e){
    		LOG.error("RSA 복호화중 오류 발생{InvalidKeyException}");
    		throw new SicimsException("RSA 암호화 키의 유효하지 않습니다. 새로고침후 다시 시도하세요");
    	} catch (UnsupportedEncodingException e) {
    		LOG.error("RSA 복호화중 오류 발생{UnsupportedEncodingException}");
    		throw new SicimsException("RSA 암호화 키의 유효하지 않습니다. 새로고침후 다시 시도하세요");
		} catch (IllegalBlockSizeException e) {
			LOG.error("RSA 복호화중 오류 발생{IllegalBlockSizeException}");
			throw new SicimsException("RSA 암호화 키의 유효하지 않습니다. 새로고침후 다시 시도하세요");
		} catch (BadPaddingException e) {
			LOG.error("RSA 복호화중 오류 발생{BadPaddingException}");
			throw new SicimsException("RSA 암호화 키의 유효하지 않습니다. 새로고침후 다시 시도하세요");
		} catch (NoSuchAlgorithmException e) {
			LOG.error("RSA 복호화중 오류 발생{NoSuchAlgorithmException}");
			throw new SicimsException("RSA 암호화 키의 유효하지 않습니다. 새로고침후 다시 시도하세요");
		} catch (NoSuchPaddingException e) {
			LOG.error("RSA 복호화중 오류 발생{NoSuchPaddingException}");
			throw new SicimsException("RSA 암호화 키의 유효하지 않습니다. 새로고침후 다시 시도하세요");
		}

        return decryptedValue;
    }

    /**
     * 16진 문자열을 byte 배열로 변환한다.
     *
     * @param hex
     * @return
     */
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) { return new byte[] {}; }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[ (int) Math.round(Math.floor(i / 2))] = value;
        }
        return bytes;
    }

    /**
     * rsa 공개키, 개인키 생성
     *
     * @param request
     */
    public static void initRsa() {
    	HttpServletRequest request = SessionUtil.getCurrentRequest();
        HttpSession session = SessionUtil.getSession();

        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance(RSA_INSTANCE);
            generator.initialize(2048);

            KeyPair keyPair = generator.genKeyPair();
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_INSTANCE);
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            session.setAttribute(RSA_WEB_KEY, privateKey); // session에 RSA 개인키를 세션에 저장

            RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            String publicKeyModulus = publicSpec.getModulus().toString(16);
            String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

            request.setAttribute("RSAModulus", publicKeyModulus); 	// rsa modulus 를 request 에 추가
            request.setAttribute("RSAExponent", publicKeyExponent); // rsa exponent 를 request 에 추가
        } catch (NoSuchAlgorithmException e) {
        	LOG.error("RSA 키 생성중 오류 발생{NoSuchAlgorithmException}");
        } catch (InvalidKeySpecException e) {
        	LOG.error("RSA 키 생성중 오류 발생{InvalidKeySpecException}");
		}
    }

    public static <T> Object getDncryptVo(T targetVo) {
    	Object resultVo = null;
		try {
			if(targetVo != null ){
				resultVo = targetVo.getClass().newInstance();
				Class<? extends Object> voClass = targetVo.getClass();

				BeanUtils.copyProperties(resultVo, targetVo);

				List<Field> fieldList = CommonUtil.getAllFields(resultVo.getClass(), 0);

				for (Field field : fieldList){
					if(field.getType() == String.class){
						String fieldNm = field.getName().substring(0, 1).toUpperCase()+ field.getName().substring(1);
						Method getter = voClass.getMethod("get"+fieldNm);
	        			Object fieldValue = getter.invoke(resultVo);
	        			if(fieldValue != null && fieldValue.toString().length() == 512 ){
	        				Method setter = voClass.getMethod("set"+fieldNm, field.getType());
	        				setter.invoke(resultVo, decryptRsa(fieldValue.toString()));
	        			}
	        		}
				}
			}
		} catch (InstantiationException e) {
			LOG.error("Vo 객체 복호화 중 오류가 발생핬습니다{InstantiationException}");
		} catch (IllegalAccessException e) {
			LOG.error("Vo 객체 복호화 중 오류가 발생핬습니다{IllegalAccessException}");
		} catch (InvocationTargetException e) {
			LOG.error("Vo 객체 복호화 중 오류가 발생핬습니다{InvocationTargetException}");
		} catch (NoSuchMethodException e) {
			LOG.error("Vo 객체 복호화 중 오류가 발생핬습니다{NoSuchMethodException}");
		} catch (SecurityException e) {
			LOG.error("Vo 객체 복호화 중 오류가 발생핬습니다{SecurityException}");
		}

    	return resultVo;
    }
}