package evan.chen.tutorial.mvvmunittest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import evan.chen.tutorial.tdd.mvpunittest.RegisterVerify

class RegisterViewModel(private val repository: IRegisterRepository) : ViewModel() {

    var registerSuccess: MutableLiveData<Event<String>> = MutableLiveData()
    var registerFail: MutableLiveData<Event<Unit>> = MutableLiveData()

    var alertText: MutableLiveData<Event<String>> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun register(loginId: String, password: String) {

        val isLoginIdOK = RegisterVerify().loginIdVerify(loginId)
        val isPwdOK = RegisterVerify().passwordVerify(password)

        if (!isLoginIdOK) {
            // 註冊失敗，資料填寫錯誤
            alertText.value = Event("帳號至少要6碼，第1碼為英文")
        } else if (!isPwdOK) {
            // 註冊失敗，資料填寫錯誤
            alertText.value = Event("密碼至少要8碼，第1碼為英文，並包含1碼數字")

        } else {
            //設定為「載入中」
            isLoading.value = true

            repository.register(loginId, password, object : IRegisterRepository.RegisterCallback {
                override fun onRegisterResult(response: RegisterResponse) {
                    if (response.registerResult) {
                        registerSuccess.value = Event(response.userId!!)
                    }else{
                        registerFail.value = Event(Unit)
                    }

                    isLoading.value = false
                }
            })
        }
    }

}