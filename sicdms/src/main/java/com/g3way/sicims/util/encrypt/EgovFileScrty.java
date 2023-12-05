/**
 *  Class Name : EgovFileScrty.java
 *  Description : Base64인코딩/디코딩 방식을 이용한 데이터를 암호화/복호화하는 Business Interface class
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.02.04    박지욱          최초 생성
 *   2017.02.07	       이정은 	      시큐어코딩(ES)-솔트 없이 일방향 해쉬함수 사용[CWE-759]
 *
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 02. 04
 *  @version 1.0
 *  @see
 *
 *  Copyright (C) 2009 by MOPAS  All right reserved.
 */
package com.g3way.sicims.util.encrypt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EgovFileScrty {

	private static final Logger LOG = LoggerFactory.getLogger(EgovFileScrty.class);

	private static final Object FILESCRTY_SYNC = new Object();

    // 파일구분자
    static final char FILE_SEPARATOR = File.separatorChar;

    static final int BUFFER_SIZE = 1024;

    /**
     * 파일을 암호화하는 기능
     *
     * @param String source 암호화할 파일
     * @param String target 암호화된 파일
     * @return boolean result 암호화여부 True/False
     * @throws IOException
     * @exception Exception
     */
    public static boolean encryptFile(String source, String target) throws IOException  {

		// 암호화 여부
		boolean result = false;

		String sourceFile = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		String targetFile = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(sourceFile);

		byte[] buffer = new byte[BUFFER_SIZE];

		if (srcFile.exists() && srcFile.isFile() && System.lineSeparator() != null && "".equals(System.lineSeparator())) {
			try (
					FileInputStream   fin =  new FileInputStream(srcFile) ;
					BufferedInputStream input = new BufferedInputStream(fin);
					FileOutputStream  fon =  new FileOutputStream(targetFile);
					BufferedOutputStream output = new BufferedOutputStream(fon);
				)
			{
					int length = 0;
					while (true) {
						length = input.read(buffer);
						if (length == -1 ) break;
						byte[] data = new byte[length];
						System.arraycopy(buffer, 0, data, 0, length);
						output.write(encodeBinary(data).getBytes());
						if(System.lineSeparator() != null && "".equals(System.lineSeparator())) {
							output.write(System.lineSeparator().getBytes());
						}
					}
					result = true;
			}
			catch (IOException e) {
				LOG.error("파일 암호화중 오류가 발생하였습니다.[IOException]");
			}
		}

		return result;
    }

    /**
     * 파일을 복호화하는 기능
     *
     * @param String source 복호화할 파일
     * @param String target 복호화된 파일
     * @return boolean result 복호화여부 True/False
     * @throws IOException
     * @exception Exception
     */
    public static boolean decryptFile(String source, String target) throws IOException  {

		// 복호화 여부
		boolean result = false;
		synchronized(FILESCRTY_SYNC) {
			String sourceFile = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
			String targetFile = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
			File srcFile = new File(sourceFile);

			String line = null;

			if (srcFile.exists() && srcFile.isFile()) {
				try (
						FileInputStream fin = new FileInputStream(srcFile);
						InputStreamReader isr = new InputStreamReader(fin);
						BufferedReader input = new BufferedReader(isr);
						FileOutputStream fon = new FileOutputStream(targetFile);
						BufferedOutputStream output = new BufferedOutputStream(fon);
					) {
					while (true) {
						line = input.readLine();
						if (line == null)
							break;
						byte[] data = line.getBytes();
						output.write(decodeBinary(new String(data)));
					}
					result = true;
				}
			}
		}
		return result;
    }

    /**
     * 데이터를 암호화하는 기능
     *
     * @param byte[] data 암호화할 데이터
     * @return String result 암호화된 데이터
     * @exception Exception
     */
    public static String encodeBinary(byte[] data)  {
		if (data == null) {
		    return "";
		}

		return new String(Base64.encodeBase64(data));
    }

    /**
     * 데이터를 복호화하는 기능
     *
     * @param String data 복호화할 데이터
     * @return String result 복호화된 데이터
     * @exception Exception
     */
    public static byte[] decodeBinary(String data)  {
    	return Base64.decodeBase64(data.getBytes());
    }

    /**
     * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
     *
     * @param password 암호화될 패스워드
     * @param id salt로 사용될 사용자 ID 지정
     * @return
     * @throws NoSuchAlgorithmException
     * @
     */
    public static String encryptPassword(String password, String id) throws NoSuchAlgorithmException  {

		if (password == null) return "";
		if (id == null) return ""; // KISA 보안약점 조치 (2018-12-11, 신용호)

		byte[] hashValue = null; // 해쉬값

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		md.reset();
		md.update(id.getBytes());

		hashValue = md.digest(password.getBytes());

		return new String(Base64.encodeBase64(hashValue));
    }

    /**
     * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
     * @param data 암호화할 비밀번호
     * @param salt Salt
     * @return 암호화된 비밀번호
     * @throws NoSuchAlgorithmException
     * @
     */
    public static String encryptPassword(String data, byte[] salt) throws NoSuchAlgorithmException  {

		if (data == null) {
		    return "";
		}

		byte[] hashValue = null; // 해쉬값

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		md.reset();
		md.update(salt);

		hashValue = md.digest(data.getBytes());

		return new String(Base64.encodeBase64(hashValue));
    }

    /**
     * 비밀번호를 암호화된 패스워드 검증(salt가 사용된 경우만 적용).
     *
     * @param data 원 패스워드
     * @param encoded 해쉬처리된 패스워드(Base64 인코딩)
     * @return
     * @throws NoSuchAlgorithmException
     * @
     */
    public static boolean checkPassword(String data, String encoded, byte[] salt) throws NoSuchAlgorithmException  {
    	byte[] hashValue = null; // 해쉬값

    	MessageDigest md = MessageDigest.getInstance("SHA-256");

    	md.reset();
    	md.update(salt);
    	hashValue = md.digest(data.getBytes());

    	return MessageDigest.isEqual(hashValue, Base64.decodeBase64(encoded.getBytes()));
    }
}
