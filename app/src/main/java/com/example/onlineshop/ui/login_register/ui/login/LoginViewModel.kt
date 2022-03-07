package com.example.onlineshop.ui.login_register.ui.login


import android.app.Application
import androidx.lifecycle.*
import com.example.onlineshop.data.entity.customer.Customer
import com.example.onlineshop.data.entity.customer.CustomerX
import com.example.onlineshop.networkBase.SingleLiveEvent
import com.example.onlineshop.repository.IRepository
import com.example.onlineshop.ui.login_register.FirebaseUserLiveData
//import com.example.onlineshop.ui.login_register.FirebaseUserLiveData
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(val repositoryImpl: IRepository, application: Application) : AndroidViewModel(application) {

    private val customerList = SingleLiveEvent<List<Customer>>()
    private val postResult = SingleLiveEvent<CustomerX?>()

    fun getPostResult(): LiveData<CustomerX?> {
        return postResult
    }

    fun getCustomerList(): LiveData<List<Customer>> {
        return customerList
    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }


    fun getAllCustomers() {
        viewModelScope.launch {
            var list = repositoryImpl.fetchCustomersData()
            list.let { customerList.postValue(list!!) }
        }
    }

    fun createCustomers(firstName: String, email: String, pass: String) {
        var customer = Customer(firstName, email, pass)
        var customerx :CustomerX?= CustomerX(customer)

        val jop = viewModelScope.launch { customerx =
            customerx?.let { repositoryImpl.createCustomers(it) }
        }
        jop.invokeOnCompletion {
            postResult.postValue(customerx)
            Timber.i("isLogged" + customerx)

        }
    }

/*
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }*/
}