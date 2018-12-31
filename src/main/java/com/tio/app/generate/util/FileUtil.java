package com.tio.app.generate.util;

import com.tio.app.common.utils.ResultUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author likui
 * @create 2018-09-29
 **/
public class FileUtil {

        private static byte[] writeImgBase64Data(String allImgData) {
            // 去掉 base64 前缀部分
            String imgData = allImgData.substring(allImgData.indexOf(";base64,") + ";base64,".length());
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                return decoder.decodeBuffer(imgData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  new  byte[]{};
        }

    /**
     * 上传图片信息
     * @param img 图片 base64
     * @param filePath 存放地址
     * @param returnPath 返回路径
     * @return
     * @throws IOException
     */
    public static ResultUtil uploadImgSrc(String img, String filePath, String returnPath) throws IOException{
        Map<String,Object> map=new HashMap<>();
        if(StringUtils.isNotEmpty(img)){
            if(img.indexOf("base64") > -1){
                byte[] byteArrayInputStream = FileUtil.writeImgBase64Data(img);
                String prefsufxx = img.substring(0, img.indexOf(";base64,") + ";base64,".length())
                        .replaceAll("data:image/", "").replaceAll(";base64,", "");
                String fileName = UUID.randomUUID() + "." + prefsufxx;
                File file = new File(filePath, fileName);
                FileUtils.writeByteArrayToFile(file, byteArrayInputStream);
                map.put("url",returnPath+"/"+fileName);
                return ResultUtil.ok().putData(map);
            }else{
                return ResultUtil.error("图片上传失败");
            }
        }else{
            return ResultUtil.error("图片上传失败");
        }
    }

    public static ResultUtil uploadVideo(MultipartFile file, String filePath) throws IOException{
        byte[] bytes=file.getBytes();
        //循环获取file数组中得文件
        String fileName = file.getOriginalFilename();
        String prefseuxx=fileName.substring(fileName.lastIndexOf(('.'))+1, fileName.length());
        if(bytes.length > 0){
            String newName = UUID.randomUUID() + "." + prefseuxx;
            File newfile = new File(filePath, newName);
            FileUtils.writeByteArrayToFile(newfile, bytes);
            Map map=new HashMap<>();
            map.put("url","/"+newName);
            ResultUtil.ok().putData(map);
        }
        return null;
    }

    /**
     * 删除图片信息
     * @param img
     * @param filePath
     * @throws IOException
     */
    public static void removeImgSrc(String img, String filePath) throws IOException{
        if(StringUtils.isNotEmpty(img)){
            File file = new File(filePath, img);
            FileUtils.forceDeleteOnExit(file);
        }
    }
}
