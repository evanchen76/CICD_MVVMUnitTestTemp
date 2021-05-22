package evan.chen.tutorial.mvvmunittest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(RegisterRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
