package com.xmasq.core.script.web;

import com.xmasq.core.script.service.IScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmasq.core.base.AjaxResponse;
import com.xmasq.core.base.web.AbstractRestController;
import com.xmasq.core.script.service.ScriptService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 脚本控制器
 * @author guoyu.huang
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/manage/script")
public class ScriptRestController extends AbstractRestController {

	@Autowired
	private IScriptService scriptService;

	/**
	 * 查询所有脚本已经执行记录
	 * @return
	 */
	@GetMapping(value = "/all")
	public AjaxResponse getAll() {
		return AjaxResponse.successData(scriptService.findAll());
	}
}
