package com.fuyusakaiori.csms.service;


import com.fuyusakaiori.csms.entity.HeadLine;

import java.util.List;

public interface HeadLineService
{
    String HEADLINESKEY = "HEADLINESKEY";

    List<HeadLine> findHeadLine(HeadLine condition);
}
