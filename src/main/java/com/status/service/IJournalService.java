package com.status.service;

import com.status.model.QueryObj;
import com.status.model.RecallObj;
import com.status.model.ResultVo;

import java.util.List;

/**
 * Created by SevenFin on 2015/9/11.
 */
public interface IJournalService {

    public List<ResultVo> queryByYear(QueryObj queryObj, RecallObj recallObj) throws Exception;

    public int updateState(QueryObj queryObj, RecallObj recallObj) throws Exception;
}
