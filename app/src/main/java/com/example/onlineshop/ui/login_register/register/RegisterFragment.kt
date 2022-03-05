package com.example.onlineshop.ui.login_register.register

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.onlineshop.R
import com.example.onlineshop.data.entity.customer.CustomerX
import com.example.onlineshop.databinding.FragmentRegisterBinding
import com.example.onlineshop.networkBase.NetworkChange
import com.example.onlineshop.ui.login_register.ui.login.LoginFragment
import com.example.onlineshop.ui.login_register.ui.login.LoginViewModel
import com.example.onlineshop.utils.Utils
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class RegisterFragment : Fragment() {
/*
   private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var signinViewModel: LoginViewModel
    private lateinit var binding: FragmentRegisterBinding
  //  private lateinit var  meDataSourceReo : MeDataSharedPrefrenceReposatory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        editor = prefs.edit()
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application
      //  val repository = RepositoryImpl(RemoteDataSourceImpl(), RoomDataSourceImpl(RoomService.getInstance(application)))

       /* val viewModelFactory = ViewModelFactory(repository,application)
        signinViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(SignInViewModel::class.java)*/
            return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().toolbar.visibility = View.GONE
       requireActivity().bottom_nav.visibility = View.GONE
        meDataSourceReo = MeDataSharedPrefrenceReposatory(requireActivity())

        binding.tvSignin.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_signUpFragment_to_signInFragment)
        )

        binding.registerBtn.setOnClickListener {
            if (!(Utils.validateEmail(binding.emailEdt.text.toString())))
                Toast.makeText(
                    context,
                    "Please check your email and try again ",
                    Toast.LENGTH_SHORT
                ).show()
            else if (!(Utils.validatePassword(binding.passwordEdt.text.toString())))
                Toast.makeText(
                    context,
                    "Please check your password and try again ",
                    Toast.LENGTH_SHORT
                ).show()
            else {
                if (NetworkChange.isOnline) {
                    signinViewModel.createCustomers(
                        binding.nameEdt.text.toString(),
                        binding.emailEdt.text.toString(),
                        binding.passwordEdt.text.toString()
                    )
                }
                else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.thereIsNoNetwork),
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
        }
        binding.passwordEdt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus){
                Timber.i("olaa passs")
                binding.group.setVisibility(View.VISIBLE)
            }
            else {
                Timber.i("olaa passsx")
                binding.group.setVisibility(View.GONE)
            }
        }

        signinViewModel.getPostResult().observe(viewLifecycleOwner, Observer<CustomerX?> {
            Log.d("Tag","signin")

            Timber.i("isLogged+" + it)
            if (it != null) {

                meDataSourceReo.saveUsertId(it.customer.id.toString())
                meDataSourceReo.saveUsertName(it.customer.firstName.toString())
                meDataSourceReo.saveUsertState(true)
                view.findNavController().popBackStack()
                Log.d("Tag","firstName != nul${it.customer.firstName.toString()}l")
            } else {
                Toast.makeText(context, "This mail is already exits", Toast.LENGTH_SHORT).show()
            }
        })
        binding.googleButton.setOnClickListener {
            val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
            launchSignInFlow(providers)
        }
        binding.facebookButton.setOnClickListener {
            val providers = arrayListOf(AuthUI.IdpConfig.FacebookBuilder().build())
            launchSignInFlow(providers)
        }


      /*  binding.registerBtn.setOnClickListener {
            if (!(Utils.validateEmail(binding.emailEdt.text.toString())))
                Toast.makeText(
                    context,
                    "Please check your email and try again ",
                    Toast.LENGTH_SHORT
                ).show()
            else if (!(Utils.validatePassword(binding.passwordEdt.text.toString())))
                Toast.makeText(
                    context,
                    "Please check your password and try again ",
                    Toast.LENGTH_SHORT
                ).show()
            else {
                if (NetworkChangeReceiver.isOnline) {
                    signinViewModel.createCustomers(
                        binding.nameEdt.text.toString(),
                        binding.emailEdt.text.toString(),
                        binding.passwordEdt.text.toString()
                    )
                }
                else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.thereIsNoNetwork),
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginFragment.SIGN_IN_RESULT_CODE) {
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
                .setIsSmartLockEnabled(false)
                .build(), LoginFragment.SIGN_IN_RESULT_CODE
        )
    }

    private fun observeAuthenticationState() {
        signinViewModel.authenticationState.observe(
            viewLifecycleOwner,
            Observer { authenticationState ->
                when (authenticationState) {
                    LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                        meDataSourceReo.saveUsertState(true)
                        Timber.i("isLogged+" + FirebaseAuth.getInstance().currentUser?.displayName + FirebaseAuth.getInstance().currentUser?.email)
                        FirebaseAuth.getInstance().currentUser?.displayName?.let {
                            FirebaseAuth.getInstance().currentUser?.email?.let { it1 ->
                                if (NetworkChange.isOnline) {
                                    signinViewModel.createCustomers(
                                        it, it1, "123"
                                    )
                                }
                                else {
                                    Toast.makeText(
                                        requireContext(),
                                        getString(R.string.thereIsNoNetwork),
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                            }
                        }
                    }
                    else -> {
                        meDataSourceReo.saveUsertState(false)
                    }
                }
            })
    }
   /* private fun validateUserName(): Boolean{
        var errorMessage: String? = null
        val value: String =binding.nameEdt.text.toString()
        if (value.isEmpty()){
            errorMessage = "User Name is required"
        }
        if (errorMessage != null){
            binding.tvNameField.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }*/

}*/
}
/*
private lateinit var mBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.nameEdt.onFocusChangeListener = this
        mBinding.emailEdt.onFocusChangeListener = this
        mBinding.passwordEdt.onFocusChangeListener = this
        mBinding.confirmPasswordEdt.onFocusChangeListener = this
    }



    private fun validateUserName(): Boolean{
        var errorMessage: String? = null
        val value: String =mBinding.nameEdt.text.toString()
        if (value.isEmpty()){
            errorMessage = "User Name is required"
        }
        if (errorMessage != null){
            mBinding.tvNameField.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }



   private fun validateEmail(): Boolean{
       var errorMessage: String? = null
       val value = mBinding.emailEdt.text.toString()
       if (value.isEmpty()){
           errorMessage = "Email address is required"
       }else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
           errorMessage = "Email address is invalid"
       }
       if (errorMessage != null){
           mBinding.tvEmailField.apply {
               isErrorEnabled = true
               error = errorMessage
           }
       }

       return errorMessage == null
   }


    private fun validatePassword(): Boolean{
        var errorMessage: String? = null
        val value = mBinding.passwordEdt.text.toString()
        if (value.isEmpty()){
            errorMessage = "Password is required"
        }else if (value.length < 6 ){
            errorMessage = "Password must be 6 characters long"
        }
        if (errorMessage != null){
            mBinding.tvPassField.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null

    }




    private fun validateConfirmPassword(): Boolean{
        var errorMessage: String? = null
        val value = mBinding.confirmPasswordEdt.text.toString()
        if (value.isEmpty()){
            errorMessage = "Confirm password is required"
        }else if (value.length < 6 ){
            errorMessage = "Confirm password must be 6 characters long"
        }
        if (errorMessage != null){
            mBinding.tvConfirmPassField.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null

    }


    private fun validatePasswordAndConfirmPassword(): Boolean{
        var errorMessage: String? = null
        val password = mBinding.passwordEdt.text.toString()
        val confirmPassword = mBinding.confirmPasswordEdt.text.toString()
        if (password != confirmPassword){
            errorMessage = "Confirm password doesn't match with password"
        }
        if (errorMessage != null){
            mBinding.tvConfirmPassField.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }



    override fun onClick(view: View?) {
        TODO("Not yet implemented")
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
         if (view != null){
             when(view.id){
                 R.id.name_edt -> {
                     if (hasFocus){
                         if (mBinding.tvNameField.isErrorEnabled){
                             mBinding.tvNameField.isErrorEnabled = false
                         }

                     }else{
                         if (validateUserName()){
                             //do validation for it's uniqueness
                         }
                     }

                 }
                 R.id.email_edt -> {
                     if (hasFocus){
                         if (mBinding.tvEmailField.isErrorEnabled){
                             mBinding.tvEmailField.isErrorEnabled = false
                         }

                     }else{
                         validateEmail()
                     }

                 }
                 R.id.password_edt -> {
                     if (hasFocus){
                         if (mBinding.tvPassField.isErrorEnabled){
                             mBinding.tvPassField.isErrorEnabled = false
                         }

                     }else{
                         if (validatePassword() && mBinding.confirmPasswordEdt.text!!.isNotEmpty() && validateConfirmPassword() &&
                                 validatePasswordAndConfirmPassword()){
                             if (mBinding.tvConfirmPassField.isErrorEnabled){
                                 mBinding.tvConfirmPassField.isErrorEnabled = false
                             }
                             mBinding.tvConfirmPassField.apply {
                                 setStartIconDrawable(R.drawable.ic_baseline_check_circle_24)
                                 setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                             }
                         }
                     }

                 }
                 R.id.confirm_password_edt -> {
                     if (hasFocus){
                         if (mBinding.tvConfirmPassField.isErrorEnabled){
                             mBinding.tvConfirmPassField.isErrorEnabled = false
                         }

                     }else{
                         if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()){
                             if (mBinding.tvPassField.isErrorEnabled){
                                 mBinding.tvPassField.isErrorEnabled = false
                             }
                             mBinding.tvConfirmPassField.apply {
                                 setStartIconDrawable(R.drawable.ic_baseline_check_circle_24)
                                 setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                             }

                         }
                     }

                 }
             }
         }
    }

    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
       return false
    }
}*/