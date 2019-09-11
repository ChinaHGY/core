package com.xmasq.core.script.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmasq.core.base.service.AbstractBaseService;
import com.xmasq.core.common.util.StringUtils;
import com.xmasq.core.script.ScriptTypeEnum;
import com.xmasq.core.script.entity.Script;
import com.xmasq.core.script.repository.ScriptRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author guoyu.huang
 */
@Slf4j
@Service
public class ScriptService extends AbstractBaseService<Script, String, ScriptRepository> implements IScriptService {

    @Autowired
    private ScriptRepository scriptRepository;

    @Override
    public List<Script> getScriptByType(ScriptTypeEnum type) {
        if (type == null) {
            log.warn("执行getScriptByType时，type为null");
            return new ArrayList<Script>(0);
        } else {
            return getRepository().findByType(type);
        }
    }

    @Override
    public Script getScriptByPath(String path, ScriptTypeEnum type) {
        if (type == null || StringUtils.isEmpty(path)) {
            log.warn("执行getScriptByType时，type为null");
            return null;
        } else {
            return getRepository().findByTypeAndPath(type, path);
        }
    }

    @Override
    public ScriptRepository getRepository() {
        return scriptRepository;
    }

    @Override
    public List<Script> findAll() {
        return super.findAll();
    }
}
