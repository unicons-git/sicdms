package com.g3way.sicims.util.common;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * FileUtil
 */
public class FileUtil {
	private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 파일을 삭제한다.
	 * @param file
	 * @return 성공하면 true, 실패하면 false
	 */
	public static boolean exist(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * 파일을 삭제한다.
	 * @param file
	 * @return 성공하면 true, 실패하면 false
	 */
	public static boolean delete(File file) {
		return FileUtils.deleteQuietly(file);
	}

	/**
	 * 파일을 삭제한다.
	 * @param file
	 * @return 성공하면 true, 실패하면 false
	 */
	public static boolean delete(String file) {

		if (StringUtil.isBlank(file)) {
			return false;
		}
		return FileUtil.delete(new File(file));
	}

	/**
	 * 디렉토리를 삭제한다.
	 * @param directory
	 */
	public static boolean deleteDirectory(File directory) {
		try {
			FileUtils.deleteDirectory(directory);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * 디렉토리내 파일을 삭제한다.
	 * @param directory
	 */
	public static boolean deleteDirectory(String directory) {
		if (StringUtil.isBlank(directory)) {
			return false;
		}
		return FileUtil.deleteDirectory(new File(directory));
	}


	/**
	 * 시스템에 따라서 파일명 인코딩이 다르다(한글처리).
	 * @param serverFilename  절대경로 파일명
	 * @return File
	 */
	public static File getServerFile(String serverFilename) {
		return new File(serverFilename);
	}

	/**
	 * 시스템에 따라서 파일명 인코딩이 다르다(한글처리).
	 * @param parentPath
	 * @param filename
	 * @return File
	 */
	public static File getServerFile(String parentPath, String filename) {
		return getServerFile(parentPath + File.separatorChar + filename);
	}

	/**
	 * 파일을 이동시킨다.
	 * @param srcFile
	 * @param destFile
	 * @return 성공하면 true, 실패하면 false
	 */
	public static boolean moveFile(File srcFile, File destFile) {
		try {
			FileUtils.moveFile(srcFile, destFile);
		}  catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * 파일을 이동시킨다.
	 * @param srcFile
	 * @param destFile
	 * @return 성공하면 true, 실패하면 false
	 */
	public static boolean moveFile(String srcFile, String destFile) {
		if (StringUtil.checkBlank(srcFile, destFile)) {
			return false;
		}
		return FileUtil.moveFile(new File(srcFile), new File(destFile));
	}

	/**
	 * 파일을 디렉토리로 이동시킨다.
	 * @param srcFile
	 * @param destFile
	 * @return 성공하면 true, 실패하면 false
	 */
	public static boolean moveFileToDirectory(String srcFile, String destDir, boolean createDestDir, String fileNm) {
		boolean isMoved 	= false;
		boolean isDeleted 	= false;
		try {
			if (exist(destDir + "/" + fileNm)) {
				isDeleted = delete(destDir + "/" + fileNm);
				if (!isDeleted) {
					// 이동이 안될 경우 소스 파일 삭제
					if (delete(srcFile)) {
						isMoved = true;
					} else {
						LOG.debug("기존 파일을 삭제할 수 없습니다.");
						isMoved = false;
					}
				} else {
					File file 		= new File(srcFile);
					File fileToMove = new File(destDir);
					FileUtils.moveFileToDirectory(file, fileToMove, createDestDir);
					isMoved = true;
				}
			} else {
				File file 		= new File(srcFile);
				File fileToMove = new File(destDir);
				FileUtils.moveFileToDirectory(file, fileToMove, createDestDir);
				isMoved = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			isMoved = false;
		}
		return isMoved;
	}


	/**
	 * 파일을 복사한다.
	 * @param srcFile
	 * @param destFile
	 * @return 성공하면 true, 실패하면 false
	 */
	public static boolean copyFile(File srcFile, File destFile) {
		try {
			FileUtils.copyFile(srcFile, destFile);
		}  catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * 파일을 복사한다.
	 * @param srcFile
	 * @param destFile
	 * @return 성공하면 true, 실패하면 false
	 */
	public static boolean copyFile(String srcFile, String destFile) {
		if (StringUtil.checkBlank(srcFile, destFile)) {
			return false;
		}

		return FileUtil.copyFile(new File(srcFile), new File(destFile));
	}


	/**
	 * 파일명이 이미 존재할 경우, 중복되지 않는 파일명을 구한다.
	 * 예) filename.jpg -> filename(1).jpg
	 * @param filename
	 * @return
	 */
	public static String notDuplicateFilename(String filename) {
		if (StringUtil.isBlank(filename)) {
			return "";
		}

		String prefix = filename;
		String ext = "";
		int dotIndex = filename.lastIndexOf(".");
		if (0 <= dotIndex) {
			prefix = filename.substring(0, dotIndex);
			ext = filename.substring(dotIndex);
		}

		prefix = prefix.replaceAll("^(.*)(\\(\\d*\\))$", "$1");

		String newFilename = filename;
		int num = 0;
		while (new File(newFilename).exists()) {
			newFilename = prefix + "(" + ++num + ")" + ext;
		}


		return newFilename;
	}

	/**
	 * 디렉토리내의 파일 목록을 조회한다.
	 * @param path
	 * @return List<HashMap<String, Object>>
	 */
	public static List<HashMap<String, Object>> getFiles(String path) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		File dirFile = new File(path);
		if (dirFile.isDirectory()) {
			File[] fileList = dirFile.listFiles();
			if(fileList != null){
				for (File file : fileList) {
					if (file.isFile()) {
						HashMap<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("fileNm", 	file.getName());
						list.add(hashMap);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 디렉토리내의 필터링 파일 목록을 조회한다.
	 * @param path
	 * @return List<HashMap<String, Object>>
	 */
	public static String[] getFiles(String path, String filter) {
		String[] fileList = null;
		File dir = new File(path);
		if (dir.isDirectory()) {
			FilenameFilter filenameFilter = new FilenameFilter() {
				public boolean accept(File f, String name) {
					return name.contains(filter);
				}
			};

			fileList = dir.list(filenameFilter);
		}
		return fileList;
	}
}
