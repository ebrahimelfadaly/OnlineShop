package com.example.onlineshop.ui.login_register.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.onlineshop.databinding.FragmentLoginBinding
import com.example.onlineshop.networkBase.NetworkChange
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

//import com.example.onlineshop.ui.login_register.databinding.FragmentLoginBinding

//import com.example.onlineshop.ui.login_register.R

class LoginFragment : Fragment() {
/*
    companion object {
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private lateinit var signinViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private var email = ""
    private var isLoginWithfirebase = false
    private lateinit var auth: FirebaseAuth
    private lateinit var meDataSourceReo: MeDataSharedPrefrenceReposatory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        val repository = RepositoryImpl(
            RemoteDataSourceImpl(),
            RoomDataSourceImpl(RoomService.getInstance(application))
        )
        val viewModelFactory = ViewModelFactory(repository, application)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        signinViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(LoginViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().toolbar.visibility = View.GONE
        requireActivity().bottom_nav.visibility = View.GONE
        meDataSourceReo = MeDataSharedPrefrenceReposatory(requireActivity())

        signinViewModel.getCustomerList().observe(viewLifecycleOwner, Observer<List<Customer>?> {
            Timber.i("we lodged")
            Timber.i("olaa sigin in email" + email)
            val customer: List<Customer> =
                it.filter {
                    it.email?.toLowerCase() ?: 0 == email
                        .toLowerCase()
                }

            if (customer.isEmpty()) {
                Toast.makeText(context, "you do not have an account", Toast.LENGTH_SHORT).show()
            } else {
                if (customer.get(0).note == binding.passwordEdt.text.toString() || isLoginWithfirebase) {

                    meDataSourceReo.saveUsertState(true)
                    meDataSourceReo.saveUsertId(customer[0].id.toString())
                    meDataSourceReo.saveUsertName(customer[0].firstName.toString())
                    Timber.i("we lodged")
                    ///   view.findNavController().currentBackStackEntry
                    view.findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "incorrect password", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.loginBtn.setOnClickListener {
            email = binding.emailEdt.text.toString().trim()
            if (NetworkChange.isOnline) {
                signinViewModel.getAllCustomers()
            }
            else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.thereIsNoNetwork),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

        binding.tvSignup.setOnClickListener {
            view.findNavController()
                .navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        binding.googleButton.setOnClickListener {
            val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
            launchSignInFlow(providers)
        }

        binding.facebookButton.setOnClickListener {
            val providers = arrayListOf(AuthUI.IdpConfig.FacebookBuilder().build())
            launchSignInFlow(providers)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                Timber.i("Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Timber.i("Sign in unsuccessful ${response?.error?.errorCode}")
            }
            observeAuthenticationState()
        }
    }

    private fun launchSignInFlow(provider: List<AuthUI.IdpConfig>) {
        // Create and launch sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                // .setIsSmartLockEnabled(false)
                .build(), SIGN_IN_RESULT_CODE
        )
    }

    private fun observeAuthenticationState() {
        signinViewModel.authenticationState.observe(
            viewLifecycleOwner,
            Observer { authenticationState ->
                when (authenticationState) {
                  LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                        email = FirebaseAuth.getInstance().currentUser?.email.toString()
                        if (NetworkChange.isOnline) {
                            signinViewModel.getAllCustomers()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.thereIsNoNetwork),
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                        isLoginWithfirebase = true
                    }
                    else -> {
                        meDataSourceReo.saveUsertState(false)
                    }
                }
            })
    }



/*
    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val loadingProgressBar = binding.loading

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/
}*/
}