$(function () {
    let changeURL = '/o2o/local/change';
    let type = getParameter('type');
    $('#submit').click(function () {
        // 获取帐号
        let username = $('#username').val();
        // 获取原密码
        let password = $('#password').val();
        // 获取新密码
        let newPassword = $('#newPassword').val();
        // 确认新密码
        let confirmPassword = $('#confirmPassword').val();
        if (newPassword !== confirmPassword) {
            $.toast('两次输入的新密码不一致！');
            return;
        }
        // 添加表单数据
        let formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);
        formData.append('newPassword', newPassword);
        // 获取验证码
        let actual = $('#j_captcha').val();
        if (!actual) {
            $.toast('请输入验证码！');
            return;
        }
        formData.append("verifyCodeActual", actual);
        // 将参数post到后台去修改密码
        $.ajax({
            url: changeURL,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！');
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

    $('#back').click(function () {
        if (type === '1') {
            // 若用户在前端展示系统页面则自动退回到前端展示系统首页
            window.location.href = '/o2o/index/';
        } else {
            // 若用户是在店家管理系统页面则自动回退到店铺列表页中
            window.location.href = '/o2o/shop-admin/shop-list';
        }
    });
});
