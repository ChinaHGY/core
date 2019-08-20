package com.xmasq.core.script;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.xmasq.core.AppContext;
import com.xmasq.core.script.entity.Script;
import com.xmasq.core.script.service.ScriptService;

import lombok.extern.slf4j.Slf4j;

/**
 * 脚本执行器
 * 
 * @author guoyu.huang
 */
@Slf4j
@Component
@Order(2)
public class ScriptExecutor implements ApplicationRunner {

	@Autowired
	private ScriptService scriptService;

	@Override
	public void run(ApplicationArguments var1) {
		init();
	}

	public void init() {

		log.info("系统开始初始化！");

		List<Script> executedScriptList = scriptService.getScriptByType(ScriptTypeEnum.CLASS);

		List<IInitScript> initScriptList = AppContext.getBeansByType(IInitScript.class);
		if (CollectionUtils.isEmpty(initScriptList)) {
			initScriptList = new ArrayList<IInitScript>();
		}

		// 针对优先序排序，排序规则：先判断version，然后sort
		initScriptList.sort(new Comparator<IInitScript>() {
			@Override
			public int compare(IInitScript o1, IInitScript o2) {
				if (o1 instanceof IPlatformInitScript && !(o2 instanceof IPlatformInitScript)) {
					return -1;
				} else if (o2 instanceof IPlatformInitScript && !(o1 instanceof IPlatformInitScript)) {
					return 1;
				} else {
					int version = compareVersion(o1.version(), o2.version());
					if (version == 0) {
						return o1.sort() - o2.sort();
					} else {
						return version;
					}
				}
			}
		});

		for (IInitScript iScriptInit : initScriptList) {
			if (isExec(executedScriptList, iScriptInit)) {
				ScriptExecuteStatusEnum executeStatus = null;
				try {
					iScriptInit.init();
					log.info("脚本：[{}]。执行状态：[成功]", iScriptInit.getClass().getSimpleName());
					executeStatus = ScriptExecuteStatusEnum.SUCCESS;
				} catch (Exception e) {
					log.error("脚本：[{}]。执行状态：[失败]。脚本路径：[{}]，失败原因:[{}]", iScriptInit.getClass().getSimpleName(),
							iScriptInit.getClass().getName(), e.getMessage());
					executeStatus = ScriptExecuteStatusEnum.FAIL;
				} finally {
					saveInitRecord(iScriptInit, ScriptTypeEnum.CLASS, executeStatus);

				}
			} else {

			}
		}

		log.info("系统初始化结束！");

	}

	private void saveInitRecord(IInitScript initScript, ScriptTypeEnum scriptType,
			ScriptExecuteStatusEnum executeStatus) {

		Script script = scriptService.getScriptByPath(initScript.getClass().getName(), scriptType);
		if (script == null) {
			script = new Script();
			script.setCreateTime(new Date());
		}
		script.setType(scriptType);
		// JAR包中的文件
		int index = initScript.getClass().getName().indexOf("$");
		if (index > 0) {
			script.setPath(initScript.getClass().getName().substring(0, index));
		} else {
			script.setPath(initScript.getClass().getName());
		}
		script.setVersion(initScript.version());
		// JAR包中的文件
		int simpleIndex = initScript.getClass().getSimpleName().indexOf("$");
		if (simpleIndex > 0) {
			script.setName(initScript.getClass().getSimpleName().substring(0, simpleIndex));
		} else {
			script.setName(initScript.getClass().getSimpleName());
		}
		script.setExecuteStatus(executeStatus);
		script.setUpdateTime(new Date());
		scriptService.saveOrUpdate(script);

	}

	private int compareVersion(String version1, String version2) {
		// 注意此处为正则匹配，不能用"."；
		String[] versionArray1 = version1.split("\\.");
		for (int i = 0; i < versionArray1.length; i++) {
			// 如果位数只有一位则自动补零（防止出现一个是04，一个是5 直接以长度比较）
			if (versionArray1[i].length() == 1) {
				versionArray1[i] = "0" + versionArray1[i];
			}
		}
		String[] versionArray2 = version2.split("\\.");
		for (int i = 0; i < versionArray2.length; i++) {
			// 如果位数只有一位则自动补零
			if (versionArray2[i].length() == 1) {
				versionArray2[i] = "0" + versionArray2[i];
			}
		}
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);
		int diff = 0;
		while (idx < minLength && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0
				&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {
			++idx;
		}
		// 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
		return diff;
	}

	private <T extends IInitScript> boolean isExec(List<Script> scriptList, T metaInit) {
		if (CollectionUtils.isEmpty(scriptList)) {
			return true;
		} else if (metaInit == null) {
			return false;
		} else {
			// JAR包中的文件
			String name = metaInit.getClass().getName();
			int index = metaInit.getClass().getName().indexOf("$");
			if (index > 0) {
				name = metaInit.getClass().getName().substring(0, index);
			}
			for (Script script : scriptList) {
				boolean sameFile = script.getPath().equals(name);
				boolean onceExecScript = ScriptExecuteTypeEnum.ONCE.equals(metaInit.executeType());
				if (sameFile && onceExecScript
						&& ScriptExecuteStatusEnum.SUCCESS.equals(script.getExecuteStatus())) {
					return false;
				}
			}
			return true;
		}
	}
}
