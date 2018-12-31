package com.tio.app.generate.controllers;

import com.tio.app.generate.config.UploadConstant;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.common.utils.UploadUtil;
import com.tio.app.generate.util.FileUtil;
import com.tio.app.sys.vo.request.CurrentUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/sys/file-upload")
public class UploadFileController {

    @Autowired
    private UploadConstant uploadConstant;

    /**
     * base64图片上传
     *
     * @param currentUserVO
     * @return
     */
    @PostMapping(value = "/base64-file")
    public ResultUtil Base64File(HttpServletResponse response, @RequestBody CurrentUserVO currentUserVO) {
        try {
            if (currentUserVO == null) {
                return ResultUtil.error("上传参数为空");
            } else {
                response.setContentType("text/html");
                return FileUtil.uploadImgSrc(currentUserVO.getBase64(), uploadConstant.getDepositPath(), uploadConstant.getReturnPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.error("系统异常");
        }
    }

    /**
     * 图片、文件上传
     *
     * @param
     * @param file
     * @return
     */
    @PostMapping(value = "/upload")
    public ResultUtil images(@RequestParam(value = "file") MultipartFile file) throws IOException {
        try {
            String url = UploadUtil.singleUpload(file, uploadConstant.getDepositPath());
            Map map = new HashMap<>();
            map.put("path", url);
            return ResultUtil.ok().putData(map);
        } catch (Exception e) {
            return ResultUtil.error("系统异常");
        }
    }


}
