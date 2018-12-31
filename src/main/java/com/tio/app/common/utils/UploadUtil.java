package com.tio.app.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class UploadUtil {
    private UploadUtil() {
    }

    /**
     * 单文件上传
     *
     * @param file
     * @param path
     * @return
     */
    public static String singleUpload(MultipartFile file, String path) {
        String fileName = null;
        System.out.println(file.getContentType() + " - " + file.getSize());
        if (!file.isEmpty()) {
            mkdirs(path);
            BufferedOutputStream buffStream = null;
            try {
                fileName = file.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf(('.')) + 1, fileName.length());
                String newFileName = UUIDUtil.randomUUID() + "." + suffix;
                byte[] bytes = file.getBytes();
                buffStream = new BufferedOutputStream(new FileOutputStream(new File(path, newFileName)));
                buffStream.write(bytes);
                buffStream.flush();
                return newFileName;
            } catch (Exception e) {
                return "You failed to upload " + fileName + ": " + e.getMessage();
            } finally {
                if (buffStream != null) {
                    try {
                        buffStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return "Unable to upload. File is empty.";
        }
    }

    /**
     * 多文件上传
     *
     * @param files
     * @param path
     * @return
     */
    public static Set<String> multiUpload(MultipartFile[] files, String path) {
        String fileName = null;
        String msg = "";
        Set<String> retSet = new HashSet<>();
        if (files != null && files.length > 0) {
            mkdirs(path);
            for (int i = 0; i < files.length; i++) {
                try {
                    fileName = files[i].getOriginalFilename();
                    String suffix = fileName.substring(fileName.lastIndexOf(('.')) + 1, fileName.length());
                    String newFileName = UUIDUtil.randomUUID() + "." + suffix;
                    byte[] bytes = files[i].getBytes();
                    BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(path, newFileName)));
                    buffStream.write(bytes);
                    buffStream.close();
                    retSet.add(fileName);
                } catch (Exception e) {
                    return retSet;
                }
            }
            return retSet;
        } else {
            return retSet;
        }
    }

    /**
     * 创建上传目录
     *
     * @param path
     */
    private static void mkdirs(String path) {
        File dirFile = new File(path);
        if (!dirFile.exists() && !dirFile.isDirectory()) {
            dirFile.mkdirs();
        }
    }
}
