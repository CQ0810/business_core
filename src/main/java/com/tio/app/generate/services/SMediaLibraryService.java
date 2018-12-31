package com.tio.app.generate.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.generate.entities.SMediaLibrary;
import org.springframework.web.multipart.MultipartFile;

public interface SMediaLibraryService extends IService<SMediaLibrary> {
    /**
     * 更新model_id
     *
     * @param modelId
     * @param where
     * @return
     */
    ResultUtil updateModelId(Integer modelId, Integer... where);

    /**
     * 上传文件处理
     *
     * @param file
     * @param modelType
     * @param collectionName
     * @param manipulations
     * @return
     */
    ResultUtil uploadFile(MultipartFile file, String modelType, String collectionName, String manipulations, Integer modelId);
}
