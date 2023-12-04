package com.g3way.sicims.util.common;

import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 업로드
 * @version  1.0
 */
public class UploadUtil {
	private static final Object UPLOAD_SYNC = new Object() ;

	private static final Logger LOG = LoggerFactory.getLogger(UploadUtil.class);

	/**
	 * 파일을 삭제한다.
	 *
	 * @param file
	 *            삭제할디렉토리
	 */
	public static void deleteFile(String file) throws FileNotFoundException {
		synchronized(UPLOAD_SYNC) {
			File f = new File(file);
			if (f.exists() && f.isFile()) {
				if(f.delete()) {
					LOG.error(f.getName() + " 파일삭제 완료");
				}
				else {
					LOG.error(f.getName() + " 파일삭제 처리 불가 ");
				}
			}
		}
	}

	/**
	 * 파일의 확장자명을 반환한다.
	 *
	 * @param 파일
	 *            이름
	 * @return 파일 확장자명
	 */
	public static String getFileExtension(String filename) {

		int idx = filename.lastIndexOf('.');
		if (idx < 0)
			return "";
		else
			return filename.substring(idx + 1);
	}


	/**
	 * 업로드 가능한 파일인지 검사 한다.
	 *
	 * @param filename
	 *            검사항 파일 path (파일명포함)
	 * @param delyn
	 *            등록 불가파일 삭제여부
	 *
	 */
//	public static boolean isValidFileUpload(String file, boolean delyn) {
//
//		String extn = null;
//
//		FilterManager fm = FilterManager.getInstance();
//
//		String pattern = fm.getString("fileupload.DeniedExtensionsFile");
//		Pattern pFilter = Pattern.compile(pattern);
//
//		Matcher mFilter = null;
//		extn = getFileExtension(file);
//
//		mFilter = pFilter.matcher(extn);
//
//		if (mFilter.matches()) {
//			deleteFile(file);
//			return false;
//		}
//
//		return true;
//
//	}

	/** MultipartFile for file upload */
	private final MultipartFile multiFile;

	/**
	 * Upload file name
	 * @uml.property  name="filename"
	 */
	private String filename;

	/**
	 * Upload file size (Byte)
	 * @uml.property  name="fileSize"
	 */
	private int fileSize;

	/** Upload Message */
	private String messagecd;

	/**
	 * 생성자
	 *
	 * @param MultipartFile
	 *            MulitparFile 객체
	 */
	public UploadUtil(MultipartFile multiFile) {

		this.multiFile = multiFile;
	}

	/**
	 * 업로드 폴더를 생성한다.
	 *
	 * @param dir
	 */
	public boolean checkdir(String fn) {
		boolean retFlag = false;
		File dir = new File(fn);
		if (dir.isDirectory()) {
			retFlag = true;
		} else {
			if (dir.mkdirs()) {

// 감리 시큐어코딩에 걸림 :
/*				dir.setExecutable(false, false);
				dir.setReadable(true, true);
				dir.setWritable(true, true);
*/				retFlag = true;
			}
		}


		return retFlag;
	}

	/**
	 * 파일업로드 실행 (파일명 중복시 일련번호 붙임.
	 *
	 * @param dir
	 *            업로드 폴더
	 */
	public boolean doUpload(String dir) {

		return doUpload(dir, getOriginalFilename());
	}

	/**
	 * 파일업로드 실행 (파일명 중복시 일련번호 붙임.)
	 *
	 * @param dir
	 *            업로드 폴더
	 * @param filename
	 *            생성할 파일명
	 */
	public boolean doUpload(String dir, String filename) {

		// 업로드 된 파일이 있는지 검사한다.
		if (null == multiFile || multiFile.isEmpty()) {
			//log.debug("multiFile Empty.");
			return false;
		}

		// 업로드 가능한 확장자 인지 검사 한다.
//		if (!isValidFileUpload(filename, false)) {
//			//log.debug("업로드 할수 없는 파일형식 입니다.[" + filename + "]");
//			return false;
//		}

		// 업로드할 폴더가 있는지 검사하고 없을경우 생성한다.
		if (!checkdir(dir)) {
			return false;
		}

		// 파일명 " " -> "_"
		String newFilename = StringUtil.replace(filename, " ", "_");

		// 파일명 중복을 피한다.
		File f = new File(FileUtil.notDuplicateFilename(dir + newFilename));

		try {
//			if (log.isDebugEnabled()) {
//				log.debug(f.getParentFile());
//				log.debug(f.getName());
//			}
			multiFile.transferTo(f);
			setFilename(f.getName());
			setFileSize(Integer.parseInt("" + (f.length() / 1024)));
		}
		catch (java.io.FileNotFoundException fnfe) {
//			if (log.isDebugEnabled()) {
//				log.debug(fnfe);
//			}
			return false;
		}
		catch (java.io.IOException ioex) {
//			if (log.isDebugEnabled()) {
//				log.debug(ioex);
//			}
			return false;
		}

		return true;
	}

	/**
	 * 파일업로드 실행 (동일 파일 있을경우 덮어쓴다.)
	 *
	 * @param dir
	 *            업로드 폴더
	 */
	public boolean doUploadOverWrite(String dir) {
		if(dir != null && !"".equals(dir) && multiFile != null){
			boolean returnboolean = doUploadOverWrite(dir, getOriginalFilename());
			return returnboolean;
		}
		else {
			return false;
		}


	}

	/**
	 * 파일업로드 실행 (동일 파일 있을경우 덮어쓴다.)
	 *
	 * @param dir
	 *            업로드 폴더
	 * @param filename
	 *            생성할 파일명
	 */
	public boolean doUploadOverWrite(String dir, String filename) {

		// 업로드 된 파일이 있는지 검사한다.
		if (multiFile == null || multiFile.isEmpty()) {
			return false;
		}

//		// 업로드 가능한 파일인지 검사 한다.
//		if (!isValidFileUpload(filename, false)) {
////			log.debug("업로드 할수 없는 파일형식 입니다.[" + filename + "]");
//			return false;
//		}

		// 업로드할 폴더가 있는지 검사하고 없을경우 생성한다.
		if (!checkdir(dir)) {
			return false;
		}

		try {
			File f = null;

			f = new File(dir + "/" + filename);

			multiFile.transferTo(f);

			setFilename(filename);
			setFileSize(Integer.parseInt("" + (f.length() / 1024)));
		}
		catch (java.io.FileNotFoundException fnfe) {
//			if (log.isDebugEnabled()) {
//				log.debug(fnfe);
//			}

			return false;
		}
		catch (java.io.IOException ioex) {
//			if (log.isDebugEnabled()) {
//				log.debug(ioex);
//			}
			return false;
		}

		return true;
	}

	/**
	 * Get file name
	 * @uml.property  name="filename"
	 */
	public String getFilename() {

		return filename;
	}

	/**
	 * Get file Size
	 * @uml.property  name="fileSize"
	 */
	public int getFileSize() {

		return fileSize;
	}

	/** Get file Size */
	public String getMessage() {

		return messagecd;
	}

	/** Get original file name */
	public String getOriginalFilename() {

		if (null == multiFile) {
			return "";
		}

		return multiFile.getOriginalFilename();
	}

	/**
	 * Set file name
	 * @uml.property  name="filename"
	 */
	public void setFilename(String filename) {

		this.filename = filename;
	}

	/**
	 * Set file size
	 * @uml.property  name="fileSize"
	 */
	public void setFileSize(int fileSize) {

		this.fileSize = fileSize;
	}

	/** Set file size */
	public void setMessage(String messagecd) {

		this.messagecd = messagecd;
	}

}
