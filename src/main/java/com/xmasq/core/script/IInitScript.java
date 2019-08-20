package com.xmasq.core.script;

/**
 * 执行脚本接口
 * 
 * @author guoyu.huang
 */
public interface IInitScript {

	static final String DEFAULT_INIT_VERSION = "1.0.0";
	static final int DEFAULT_INIT_SORT = 2;
	static final int AFTER_BEFORE_ALL_SORT = 1;
	static final int BEFORE_ALL_SORT = 0;

	/**
	 * 初始化脚本的顺序，必须是正整数，默认为1，0为占用顺序，最优先执行。 默认只允许有一个前置执行器，如果有多个，可以在前置执行器里面实现业务逻辑
	 *
	 * @return
	 */
	default int sort() {
		return DEFAULT_INIT_SORT;
	}

	/**
	 * 默认版本为1.0.0
	 * 
	 * @return
	 */
	default String version() {
		return DEFAULT_INIT_VERSION;
	}

	/**
	 * 默认没有备注
	 * 
	 * @return
	 */
	default String memo() {
		return "";
	}

	/**
	 * 执行状态，默认为一次
	 * 
	 * @return
	 */
	default ScriptExecuteTypeEnum executeType() {
		return ScriptExecuteTypeEnum.ONCE;
	}

	/**
	 * 初始化内容
	 */
	void init();
}
