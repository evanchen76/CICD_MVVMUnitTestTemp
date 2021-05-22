package evan.chen.tutorial.mvvmunittest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    private lateinit var repository: IRegisterRepository

    @Before
    fun setupPresenter() {
        MockKAnnotations.init(this)
    }

    @Test
    fun registerWrongId() {
        val loginId = "111"
        val password = "222"

        val viewModel = RegisterViewModel(repository)
        viewModel.register(loginId, password)

        Assert.assertEquals("帳號至少要6碼，第1碼為英文", viewModel.alertText.value?.getContentIfNotHandled())
    }

    @Test
    fun registerWrongPassword() {
        val loginId = "A11111111"
        val password = "222"

        val viewModel = RegisterViewModel(repository)
        viewModel.register(loginId, password)

        Assert.assertEquals("密碼至少要8碼，第1碼為英文，並包含1碼數字", viewModel.alertText.value?.getContentIfNotHandled())
    }

    @Test
    fun registerFail() {
        val loginId = "A11111111"
        val password = "A11111112"

        val slot = slot<IRegisterRepository.RegisterCallback>()

        every { repository.register(eq(loginId), eq(password), capture(slot)) }
            .answers {
                slot.captured.onRegisterResult(
                    RegisterResponse(
                        false,
                        null
                    )
                )
            }

        val viewModel = RegisterViewModel(repository)
        viewModel.register(loginId, password)

        Assert.assertFalse(viewModel.isLoading.value!!)
        Assert.assertEquals(Unit, viewModel.registerFail.value?.getContentIfNotHandled())
    }

    @Test
    fun registerSuccess() {
        val loginId = "A11111111"
        val password = "A11111112"

        val userId = "AnyId"

        val slot = slot<IRegisterRepository.RegisterCallback>()

        every { repository.register(eq(loginId), eq(password), capture(slot)) }
            .answers {
                slot.captured.onRegisterResult(
                    RegisterResponse(
                        true,
                        userId
                    )
                )
            }

        val viewModel = RegisterViewModel(repository)
        viewModel.register(loginId, password)

        Assert.assertEquals(userId, viewModel.registerSuccess.value?.getContentIfNotHandled())
        Assert.assertFalse(viewModel.isLoading.value!!)

    }

    @Test
    fun registerShouldCallRepository() {
        val loginId = "A11111111"
        val password = "A11111112"

        val slot = slot<IRegisterRepository.RegisterCallback>()

        val viewModel = RegisterViewModel(repository)
        viewModel.register(loginId, password)

        verify { repository.register(eq(loginId), eq(password), capture(slot)) }

        Assert.assertTrue(viewModel.isLoading.value!!)
    }
}