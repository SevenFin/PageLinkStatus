package com.status.dao;

import com.status.model.ResultSingle;
import com.status.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by SevenFin on 2015/9/11.
 */
public interface IJournalDao {

    public List<ResultSingle> queryByYear(@Param("schemaName") String schemaName,
                                          @Param("vendor") String vendor, @Param("type") Integer type,
                                          @Param("condition") String condition) throws Exception;

    public List<ResultSingle> queryJinfo(@Param("schemaName") String schemaName,
                                         @Param("vendor") String vendor, @Param("type") Integer type,
                                         @Param("condition") String condition) throws Exception;

    public Integer updateState(@Param("schemaName") String schemaName,
                               @Param("vendor") String vendor, @Param("type") Integer type,
                               @Param("condition") String condition, @Param("stateCondition") String stateCondition,
                               @Param("yearCondition") String yearCondition, @Param("stateTo") String stateTo) throws Exception;

    public Integer updateJinfo(@Param("schemaName") String schemaName,
                               @Param("vendor") String vendor, @Param("type") Integer type,
                               @Param("condition") String condition, @Param("stateCondition") String stateCondition,
                               @Param("stateTo") String stateTo) throws Exception;
}
