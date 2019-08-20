// 公共使用的函数，非工具函数
var commonCt = function() {
	return {
		path : 'http://' + window.location.host,
		// 显示密码修改弹窗
		showUpdatePasswordDialog: function(){
			$('#update-password-dialog').modal('show');
		},
		// 密码修改
		updPassword : function() {
			var newPassword1 = $('input[name="newPassword1"]').val()
			var	newPassword2 = $('input[name="newPassword2"]').val();
			var	oldPassword = $('input[name="oldPassword"]').val();
			if (!newPassword1) {
				commonCt.showUpdatePasswordFormTip('密码不能为空');
				return;
			}
			if(newPassword1 != newPassword2){
				commonCt.showUpdatePasswordFormTip('两次密码不一致');
				return;
			}
			var password = $.md5(newPassword1);
			oldPassword = $.md5(oldPassword);
			var data = {};
			data.newPassword = password;
			data.oldPassword = oldPassword;
			request.ajaxPut( this.path + '/manage/update-password', data, function(result){
				if (result.success) {
					window.location.href='/manage/loginpage'
				}else{
					commonCt.showUpdatePasswordFormTip('修改失败！原因：' + result.message);
				}
			}, function(){
				commonCt.showUpdatePasswordFormTip('修改失败！原因：服务器连接！');
			});
		},
		// 退出登录
		loginOut : function() {
			request.ajaxGet(this.path + '/manage/logout', function(result){
				if (result.success) {
					window.location.href = '/manage/loginpage';
				}
			});
		},
		// 显示登录弹窗
		showLoginDialog: function(){
			$('#login-dialog').modal('show');
		},
		// 登录
		login : function(){
			var $username = $('#login-dialog').find('input[name="username"]');
			if(!$username.val()){
				$username.focus();
				return;
			}
			var $password = $('#login-dialog').find('input[name="password"]');
			if(!$password.val()){
				$password.focus();
				return;
			}
			var data = {};
			data.username = $username.val();
			data.password = $.md5($password.val());
			request.ajaxPost(this.path + '/manage/login', data, function(result){
				if (result.success) {
					$('#login-dialog').modal('hide');
					commonCt.tipOk('登录成功')
				} else {
					commonCt.showLoginFormTip('登录失败！原因：'+ result.message)
				}
			},  function() {
				commonCt.showLoginFormTip('登录失败！连接服务器异常')
			});
		},
		// 构建分页面板
		buildPaging: function(totalRecord, page, size, subClass){
			
			if(totalRecord <= size){ // 1.只有数量不足一页
				return '';
			}else{
				var pagingContent = '<ul class="pagination">';
				
				// 上一页
				if(page <= 1){
					pagingContent += '<li class="disabled xmasq-hand"><a>&lt;</a></li>';
				}else{
					pagingContent += '<li class="xmasq-hand" onclick="'+ subClass +'.query('+ (page-1) +')"><a>&lt;</a></li>';
				}
				
				// 中间具体的页数，取下限
				var total;
				if(totalRecord % size > 0){
					total = parseInt(totalRecord / size) + 1;			
				}else{
					total = parseInt(totalRecord / size);
				}
				
				if(total <= 7){// 未超过七页，全部显示
					for(var i = 1; i <= total; i++){
						if(page == i){
							pagingContent += '<li class="active xmasq-hand"><a>'+ i +'</a></li>';
						}else{
							pagingContent += '<li class="xmasq-hand" onclick="'+ subClass +'.query('+ i +')"><a>'+ i +'</a></li>';
						}
					}
				}else if(page > 4){ // 超过七页，当前页超过4页
					pagingContent += '<li class="xmasq-hand" onclick="'+ subClass +'.query(1)"><a>1</a></li>';
					pagingContent += '<li><a>...</a></li>';
					
					if(total - page < 4){ // 最后5页
						for(var i = 4; i >= 0; i--){					
							var index = total - i;				
							if(page == index){
								pagingContent += '<li class="active xmasq-hand"><a>'+ index +'</a></li>';
							}else{
								pagingContent += '<li class="xmasq-hand" onclick="'+ subClass +'.query('+ index- +')"><a>'+ index +'</a></li>';
							}
						}
					}else{
						var pre = page - 1;
						pagingContent += '<li class="xmasq-hand" onclick="'+ subClass +'.query('+ pre +')"><a>'+ pre +'</a></li>';
						pagingContent += '<li class="active xmasq-hand"><a>'+ page +'</a></li>';
						var next = page + 1;
						pagingContent += '<li class="xmasq-hand" onclick="'+ subClass +'.query('+ next +')"><a>'+ next +'</a></li>';
						pagingContent += '<li><a>...</a></li>';				
						pagingContent += '<li class="xmasq-hand" onclick="'+ subClass +'.query('+ total +')"><a>'+ total +'</a></li>';
					}
					
				}else{// 超过七页，当前页未超过4页
					for(var i = 1; i < 5; i++){
						if(page == i){
							pagingContent += '<li class="active xmasq-hand"><a>'+ i +'</a></li>';
						}else{
							pagingContent += '<li class="xmasq-hand" onclick="'+ subClass +'.query('+ i +')"><a>'+ index +'</a></li>';
						}
					}
					pagingContent += '<li><a>...</a></li>';				
					pagingContent += '<li class="xmasq-hand" onclick="'+ subClass +'.query('+ total +')"><a>'+ total +'</a></li>';
				}
						
				if(page >= total){
					pagingContent += '<li class="disabled xmasq-hand"><a>&gt;</a></li>';
				}else{
					pagingContent += '<li class="xmasq-hand" onclick="'+ subClass +'.query('+ (page+1) +')"><a>&gt;</a></li>';
				}	
				pagingContent += '</ul>';
				return pagingContent;	
			}
		},
		// 构建按钮
		buildBtn: function(name, event, className){
			className = className || 'btn-primary';
			return '<button type="button" class="xmasq-table-btn btn '+ className +'"  onclick=\''+ event +'\'>'+ name +'</button>';
		},
		// 构建下拉按钮
		buildDropdownBtn : function(name, event, className){
			className = className || 'btn-danger';
			var btns = '<div class="btn-group">';
			btns += '<button type="button" class="btn '+ className +' dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">';
			btns += name + ' <span class="caret"></span>';
			btns += '</button>';
			btns += '<ul class="dropdown-menu" style="min-width:0px;">';
			btns += '<li><a href="#" onclick=\''+ event +'\'>确认</a></li>';
			btns += '</ul>';
			btns += '</div>';
			return btns;
		},
		// 跳转到菜单中的链接
		toMenu: function (el){
			var url = $(el).attr('data-url');
			location.href = url;
		},
		// 聚焦菜单
		focusMenu: function (elementId){
			$('.xmasq-focus').removeClass('xmasq-focus');
			$('#' + elementId).addClass('xmasq-focus');
			$('#' + $('#' + elementId).attr('data-parent')).click();
		},
		// 成功提示
		tipOk: function(text, title, timeSec){
			commonCt.buildTip('alert-success', text, title, timeSec)
		},
		// 警告提示
		tipWarn: function(text, title, timeSec){
			commonCt.buildTip('alert-warn', text, title, timeSec)
		},
		// 失败提示
		tipFail: function(text, title, timeSec){
			commonCt.buildTip('alert-error', text, title, timeSec)
		},
		// 构建提示
		buildTip: function(level, text, title, timeSec){
			var content = '<div class="alert '+ level +'"><button type="button" class="close" data-dismiss="alert">×</button>';
			if(title){
				content += '<h4>'+title+'</h4>';	
			}
			content += text + '</div>';
			
			$('#global-tip').empty().html(content);
			timeSec = timeSec ? timeSec : 2000;
			setTimeout(function(){
				$('#global-tip').hide();
			}, timeSec);
			
			$('#global-tip').show();
		},
		// 显示表单提示
		showFormTip: function(text, timeSec){
			var error = $('#dialog').find('div.xmasq-form-error');
			error.text(text);
			if(timeSec){
				setTimeout(function(){
					error.hide();
				}, timeSec);
			}
			error.show();
		},
		// 显示修改密码表单提示
		showUpdatePasswordFormTip: function(text, timeSec){
			var error = $('#update-password-dialog').find('div.xmasq-form-error');
			error.text(text);
			if(timeSec){
				setTimeout(function(){
					error.hide();
				}, timeSec);
			}
			error.show();
		},
		// 显示登录表单提示
		showLoginFormTip: function(text, timeSec){
			var error = $('#login-dialog').find('div.xmasq-form-error');
			error.text(text);
			if(timeSec){
				setTimeout(function(){
					error.hide();
				}, timeSec);
			}
			error.show();
		},
		// 重置表单
		resetForm: function(){
		    // 重置form表单
		    $('.form-horizontal')[0].reset();
		    // 隐藏异常
		    $('.xmasq-form-error').hide();
		}
	};
}();

// 请求函数
var request = function(){
	return {
		ajaxGet: function (url, successCallBack, errorCallBack, async, dataType){
			$.ajax({
				url: url,
				type: "GET",
				async: (typeof(async) == "undefined" ? true : async),
				dataType: (dataType ? dataType : 'json'),
				contentType: "application/json; charset=utf-8",
				success: function(result){
					if(result.message === 'Session-TimeOut'){
						commonCt.showLoginDialog();
					}else if(successCallBack && typeof(successCallBack) == 'function'){
						successCallBack(result);
					}
				},
				error: function(request, status, error){
					if(errorCallBack && typeof(errorCallBack) == 'function'){
						errorCallBack(request, status, error);
					}
				}
			});
		},
		ajaxPost: function (url, data, successCallBack, errorCallBack, async, dataType){
			$.ajax({
				url: url,
				type: "POST",
				async: (typeof(async) == "undefined" ? true : async),
				dataType: (dataType ? dataType : 'json'),
				contentType: "application/json",
				data: JSON.stringify(data),
				success: function(result){
					if(result.message === 'Session-TimeOut'){
						commonCt.showLoginDialog();
					}else if(successCallBack && typeof(successCallBack) == 'function'){
						successCallBack(result);
					}
				},
				error: function(request, status, error){
					if(errorCallBack && typeof(errorCallBack) == 'function'){
						errorCallBack(request, status, error);
					}
				}
			});
		},
		ajaxPut: function (url, data, successCallBack, errorCallBack){
			$.ajax({
				url: url,
				type: "PUT",
				async: true,
				dataType: 'json',
				contentType: "application/json",
				data: JSON.stringify(data),
				success: function(result){
					if(result.message === 'Session-TimeOut'){
						commonCt.showLoginDialog();
					}else if(successCallBack && typeof(successCallBack) == 'function'){
						successCallBack(result);
					}
				},
				error: function(request, status, error){
					if(errorCallBack && typeof(errorCallBack) == 'function'){
						errorCallBack(request, status, error);
					}
				}
			});
		},
		ajaxDelete: function (url, successCallBack, errorCallBack){
			$.ajax({
				url: url,
				type: "DELETE",
				async: true,
				dataType: 'json',
				contentType: "application/json",
				success: function(result){
					if(result.message === 'Session-TimeOut'){
						commonCt.showLoginDialog();
					}else if(successCallBack && typeof(successCallBack) == 'function'){
						successCallBack(result);
					}
				},
				error: function(request, status, error){
					if(errorCallBack && typeof(errorCallBack) == 'function'){
						errorCallBack(request, status, error);
					}
				}
			});
		}
	}
}();

var business = function(){
    return {
        getStatus: function(status){
            if('UNUSED' == status){
                return '未使用';
            }else if('NORMAL' == status){
                return '正常';
            }else if('DELETED' == status){
                 return '已删除';
            }else if('OTHER' == status){
                 return '其他';
            }else {
                return '未知';
            }
        }
    }
}();