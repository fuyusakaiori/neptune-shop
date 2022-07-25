package com.neptune.shop.core.service;


import com.neptune.shop.core.entity.HeadLine;

import java.util.List;

public interface HeadLineService
{
    String HEADLINESKEY = "HEADLINESKEY";

    List<HeadLine> findHeadLine(HeadLine condition);
}
