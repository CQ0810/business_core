package com.tio.app.generate.entities;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SMediaLibrary {
    @TableId("id")
    private int mediaId;

    private String modelType;
    private int modelId;
    private String collectionName;
    private String name;
    private String fileName;
    private String mimeType;
    private String disk;
    private long size;
    private String manipulations;
    private String customProperties;
    private String responsiveImages;
    private int orderColumn;
    @TableField(fill = FieldFill.INSERT)
    private int createdAt;
    @TableField(fill = FieldFill.UPDATE)
    private int updatedAt;
}


