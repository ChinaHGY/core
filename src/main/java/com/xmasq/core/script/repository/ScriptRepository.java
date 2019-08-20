package com.xmasq.core.script.repository;

import java.util.List;

import com.xmasq.core.base.repository.BaseRepository;
import com.xmasq.core.script.ScriptTypeEnum;
import com.xmasq.core.script.entity.Script;

/**
 * 脚本持久类
 * @author guoyu.huang
 * @version 1.0.0
 */
public interface ScriptRepository extends BaseRepository<Script, String> {

	/**
	 * 查询指定脚本类型的脚本执行记录
	 * 
	 * @param type
	 * @return
	 */
	List<Script> findByType(ScriptTypeEnum type);

	/**
	 * 查询指定脚本的执行记录
	 * 
	 * @param type
	 * @param path
	 * @return
	 */
	Script findByTypeAndPath(ScriptTypeEnum type, String path);
}
