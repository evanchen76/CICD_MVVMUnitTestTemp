package evan.chen.tutorial.tdd.mvpunittest

class RegisterVerify {

    fun loginIdVerify(loginId: String): Boolean {
        var isLoginIdOK = false
        //帳號至少6碼，第1碼為英文
        if (loginId.length >= 6) {
            if (loginId.toUpperCase().first() in 'A'..'Z') {
                isLoginIdOK = true
            }
        }
        return isLoginIdOK
    }

    fun passwordVerify(pwd: String): Boolean {
        var isPwdOK = false
        //密碼至少8碼，第1碼為英文，並包含1碼數字
        if (pwd.length >= 8) {
            if (pwd.toUpperCase().first() in 'A'..'Z') {
                if (pwd.findAnyOf((0..9).map { it.toString() }) != null) {
                    isPwdOK = true
                }
            }
        }
        return isPwdOK
    }
}