package com.tio.app.generate.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tio.app.common.pojo.ResultCode;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.common.utils.UploadUtil;
import com.tio.app.generate.config.UploadConstant;
import com.tio.app.generate.entities.SMediaLibrary;
import com.tio.app.generate.mappers.SMediaLibraryMapper;
import com.tio.app.generate.services.SMediaLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class SMediaLibraryServiceImpl extends ServiceImpl<SMediaLibraryMapper, SMediaLibrary> implements SMediaLibraryService {
    @Autowired
    private UploadConstant uploadConstant;

    @Override
    public ResultUtil updateModelId(Integer modelId, Integer... where) {
        List<SMediaLibrary> list = new ArrayList<>();
        for (Integer id : where) {
            SMediaLibrary sMediaLibrary = getById(id);
            sMediaLibrary.setModelId(modelId);
            list.add(sMediaLibrary);
        }
        boolean b = updateBatchById(list);
        if (!b) {
            return ResultUtil.error(ResultCode.UPDATE_DATA_ERROR);
        }
        return ResultUtil.ok().putData(list);
    }

    @Override
    public ResultUtil uploadFile(MultipartFile file, String modelType, String collectionName, String manipulations, Integer modelId) {
        String fileName = UploadUtil.singleUpload(file, uploadConstant.getDepositPath());
        String contentType = file.getContentType();
        SMediaLibrary sMediaLibrary = new SMediaLibrary();
        sMediaLibrary.setSize(file.getSize());
        sMediaLibrary.setFileName(fileName);
        sMediaLibrary.setDisk(uploadConstant.getDepositPath());
        sMediaLibrary.setMimeType(contentType);
        sMediaLibrary.setModelType(modelType);
        sMediaLibrary.setCollectionName(collectionName);
        sMediaLibrary.setName(fileName.substring(0, fileName.lastIndexOf(".")));
        sMediaLibrary.setManipulations(manipulations);
        sMediaLibrary.setModelId(modelId);
        save(sMediaLibrary);
        return ResultUtil.ok().putData(sMediaLibrary);
    }
}
