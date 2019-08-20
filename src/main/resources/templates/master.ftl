<!DOCTYPE html>

<#include "global-constants.ftl"/>
<#include "system-resource.ftl"/>

<html lang="en">
<head>

<!-- IE 兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<link rel="shortcut icon" href="${path}/images/favicon.ico" type="image/x-icon" />
<!-- <link href="${path}/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" />  -->
<link href="${path}/plugins/bootstrap/css/bootstrap-datetimepicker.css" rel="stylesheet" />
<link href="${path}/plugins/bootstrap/css/bootstrap-combined.min.css" rel="stylesheet" />
<link href="${path}/platform/css/xmasq.css" rel="stylesheet" />

<title>${systemName}</title>

</head>

<!--basic scripts-->
<script src='${path}/plugins/jquery/jquery-1.9.1.min.js'></script>
<script src='${path}/plugins/bootstrap/js/bootstrap.min.js'></script>
<script
	src='${path}/plugins/bootstrap/js/bootstrap-datetimepicker.min.js'></script>
<script
	src='${path}/plugins/bootstrap/js/bootstrap-datetimepicker.zh-CN.js'></script>

<!--inline scripts related to this page-->
<script src='${path}/plugins/jquery/jquery.md5.js'></script>
<script src='${path}/platform/js/common.js'></script>

<body>
	<#include "header.ftl"/>
	<div class="row-fluid">
		<div class="span2 xmasq-menu">
			<#include "menu.ftl"/>
		</div>
		<div class="span10 xmasq-content">
			<@body/>
		</div>
	</div>
	
	<div id="global-tip" class="xmasq-tip hide"></div>
	
	<div id="update-password-dialog" class="modal hide fade in" role="dialog" aria-hidden="false">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="dialog-title">修改密码</h3>
		</div>
		<div class="modal-body">
			<form class="form-horizontal">
				<div class="control-group xmasq-form-error hide"></div>
				<div class="control-group">
					<label class="control-label">旧密码</label>
					<div class="controls">
						<input name="oldPassword" type="password" placeholder="请输入旧密码">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">新密码</label>
					<div class="controls">
						<input name="newPassword1" type="password" placeholder="请输入新密码">
					</div>
					</div>
				<div class="control-group">
					<label class="control-label">确认密码</label>
					<div class="controls">
						<input name="newPassword2" type="password" placeholder="请输入新密码">
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			<button class="btn btn-primary" onclick="commonCt.updPassword()">保存</button>
		</div>
	</div>
	<div id="login-dialog" class="modal hide fade" role="dialog" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="dialog-title">重新登录</h3>
		</div>
		<div class="modal-body">
			<form class="form-horizontal">
				<div class="control-group xmasq-form-error hide"></div>
				<div class="control-group">
					<label class="control-label">账号</label>
					<div class="controls">
						<input name="username" type="text" placeholder="请输入账号">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">密码</label>
					<div class="controls">
						<input name="password" type="password" placeholder="请输入密码">
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			<button class="btn btn-primary" onclick="commonCt.login()">登录</button>
		</div>
				
	</div>
</body>

</html>