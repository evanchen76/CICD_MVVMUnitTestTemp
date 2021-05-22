package evan.chen.tutorial.mvvmunittest

import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import evan.chen.tutorial.mvvmunittest.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, ViewModelFactory()).get(RegisterViewModel::class.java)

        binding.send.setOnClickListener {
            val loginId = binding.loginId.text.toString()
            val password = binding.password.text.toString()

            viewModel.register(loginId, password)
        }

        viewModel.alertText.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(it)
                    .setTitle("錯誤")

                builder.show()
            }
        })

        viewModel.registerSuccess.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                val loginId = it
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("ID", loginId)

                startActivity(intent)
            }
        })

        viewModel.registerFail.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("註冊失敗")
                        .setTitle("錯誤")

                    builder.show()
                }
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        })
    }

}
