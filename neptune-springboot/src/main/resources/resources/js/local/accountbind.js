$(function() {
	let bindURL = '/o2o/local/register';
	// type=1 角色为顾客,type=2 角色为店家, type=0 角色为后台管理员
	let type = getParameter('type');
	$('#submit').click(function() {
		// 获取输入的帐号
		let username = $('#username').val();
		// 获取输入的密码
		let password = $('#password').val();
		// 获取输入的验证码
		let actual = $('#j_captcha').val();
		if (!actual) {
			$.toast('请输入验证码！');
			return;
		}
		// 访问后台，绑定帐号
		$.ajax({
			url : bindURL,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				username : username,
				password : password,
				verifyCodeActual : actual
			},
			success : function(data) {
				if (data.success) {
					$.toast('绑定成功！');
					if (type === '1') {
						// 若用户在前端展示系统页面则自动退回到前端展示系统首页
						window.location.href = '/o2o/index/';
					} else {
						// 若用户是在店家管理系统页面则自动回退到店铺列表页中
						window.location.href = '/o2o/shop-admin/shop-list';
					}

				} else {
					$.toast('提交失败！' + data.message);
					$('#captcha_img').click();
				}
			}
		});
	});
});