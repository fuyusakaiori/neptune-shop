package com.o2o.service;

import com.o2o.entity.HeadLine;

import java.util.List;

public interface HeadLineService
{
    List<HeadLine> findHeadLine(HeadLine condition);
}
