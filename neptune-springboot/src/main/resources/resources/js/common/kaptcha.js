/**
 * <h2>获取验证码</h2>
 */
function changeVerifyCode(img) {
    img.src = '../kaptcha?' + Math.floor(Math.random() * 100);
}