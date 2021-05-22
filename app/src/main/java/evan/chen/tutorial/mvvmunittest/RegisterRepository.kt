package evan.chen.tutorial.mvvmunittest

interface IRegisterRepository {
    fun register(loginId: String, password: String, callback: RegisterCallback)

    interface RegisterCallback {
        fun onRegisterResult(response: RegisterResponse)
    }
}

class RegisterRepository : IRegisterRepository {

    override fun register(
        loginId: String,
        password: String,
        callback: IRegisterRepository.RegisterCallback
    ) {
        if (loginId == "EvanChen") {
            //模擬有重覆註冊 => 註冊失敗
            callback.onRegisterResult(
                RegisterResponse(false, null)
            )
        }else{
            callback.onRegisterResult(
                RegisterResponse(true, "AnyId")
            )
        }
    }
}

