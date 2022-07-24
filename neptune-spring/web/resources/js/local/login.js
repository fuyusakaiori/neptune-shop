$(function() {
	let loginURL = '/o2o/local/login';
	let type = getParameter('type');
	let loginCount = 0;

	$('#submit').click(function() {
		// 获取输入的帐号
		let username = $('#username').val();
		// 获取输入的密码
		let password = $('#password').val();
		// 获取验证码信息
		let verifyCodeActual = $('#j_captcha').val();
		// 是否需要验证码验证，默认为false,即不需要
		let needVerify = false;
		// 如果登录三次都失败
		if (loginCount >= 3) {
			// 那么就需要验证码校验了
			if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		// 访问后台进行登录验证
		$.ajax({
			url : loginURL,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				username : username,
				password : password,
				verifyCodeActual : verifyCodeActual,
				//是否需要做验证码校验
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					$.toast('登录成功！');
					if (type === '1') {
						// 若用户在前端展示系统页面则自动链接到前端展示系统首页
						window.location.href = '/o2o/index/';
					} else {
						// 若用户是在店家管理系统页面则自动链接到店铺列表页中
						window.location.href = '/o2o/shop-admin/shop-list';
					}
				} else {
					$.toast('登录失败！' + data.message);
					loginCount++;
					if (loginCount >= 3) {
						// 登录失败三次，需要做验证码校验
						$('#verifyPart').show();
					}
				}
			}
		});
	});
});