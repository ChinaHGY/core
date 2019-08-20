package com.xmasq.core.script.service;

import com.xmasq.core.script.ScriptTypeEnum;
import com.xmasq.core.script.entity.Script;

import java.util.List;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public interface IScriptService {

    /**
     * 根据类型获取所有脚本执行记录
     *
     * @param type
     * @return 当 type == null时，返回new ArrayList<Script>(0)
     */
    List<Script> getScriptByType(ScriptTypeEnum type);

    /**
     * 根据类型获取所有脚本执行记录
     *
     * @param path
     * @param type
     * @return
     */
    Script getScriptByPath(String path, ScriptTypeEnum type);

    /**
     * 查询所有脚本的执行记录
     * @return
     */
    List<Script> findAll();

}
