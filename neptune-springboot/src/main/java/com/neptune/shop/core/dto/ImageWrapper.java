package com.neptune.shop.core.dto;

import java.io.InputStream;

/**
 * <h2>封装图片流和文件名</h2>
 */
public class ImageWrapper
{
    private InputStream image;

    private String filename;

    public InputStream getImage()
    {
        return image;
    }

    public void setImage(InputStream image)
    {
        this.image = image;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }
}
