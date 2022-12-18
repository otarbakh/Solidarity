package com.example.solidarity.ui.otp_register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.solidarity.R
import com.example.solidarity.common.BaseFragment
import com.example.solidarity.databinding.FragmentOtpRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class OtpRegisterFragment :
    BaseFragment<FragmentOtpRegisterBinding>(FragmentOtpRegisterBinding::inflate),
    AdapterView.OnItemSelectedListener {


    var mVerificationId: String? = null
    var mResendToken: PhoneAuthProvider.ForceResendingToken? = null

    private lateinit var auth: FirebaseAuth
    private var selectedCountryCode = ""

    override fun viewCreated() {

        auth = Firebase.auth
        setupSpinner()
    }

    override fun listeners() {
        checkVerificationCode()
        sendOtp()
        changeColours()
        checkVisibility()
        checknext()
    }


    private fun changeColours() {
        binding.btnEnglish.setOnClickListener {
            binding.btnEnglish.setBackgroundResource(com.example.solidarity.R.drawable.button_outline_gray)

            binding.btnGeorgian.setBackgroundResource(com.example.solidarity.R.drawable.button_outline_blue)

            binding.btnUkrainian.setBackgroundResource(com.example.solidarity.R.drawable.button_outline_blue)

        }
        binding.btnGeorgian.setOnClickListener {
            binding.btnGeorgian.setBackgroundResource(com.example.solidarity.R.drawable.button_outline_gray)

            binding.btnEnglish.setBackgroundResource(com.example.solidarity.R.drawable.button_outline_blue)

            binding.btnUkrainian.setBackgroundResource(com.example.solidarity.R.drawable.button_outline_blue)

        }

        binding.btnUkrainian.setOnClickListener {
            binding.btnUkrainian.setBackgroundResource(com.example.solidarity.R.drawable.button_outline_gray)

            binding.btnEnglish.setBackgroundResource(com.example.solidarity.R.drawable.button_outline_blue)

            binding.btnGeorgian.setBackgroundResource(com.example.solidarity.R.drawable.button_outline_blue)

        }
    }

    private fun checkVisibility() {
        binding.etPhoneNumber.doOnTextChanged { text, start, before, count ->
            binding.btnNext.setBackgroundResource(com.example.solidarity.R.drawable.button_outline_blue)

        }
        if (binding.etPhoneNumber.text!!.isNotEmpty()) {
            binding.btnNext.isClickable
        }
    }


    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            com.example.solidarity.R.array.country_codes,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.indexSpinner.adapter = adapter
        binding.indexSpinner.onItemSelectedListener = this

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        selectedCountryCode = text
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun checknext() {
        binding.btnNext.setOnClickListener {
            Toast.makeText(requireContext(), "gogogogo", Toast.LENGTH_SHORT).show()
        }
    }


    private fun sendOtp() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+995577404545") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("baxtadze", "gaigzavna kodi")

    }


    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //Getting the code sent by SMS
                val code = credential.smsCode
                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code
                code?.let { verifyVerificationCode(it) }
                Log.d("baxtadze", code.toString())
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Log.d("baxtadze", e.message.toString())

                    // Invalid request
                    // ...
                } else if (e is FirebaseTooManyRequestsException) {
                    Log.d("baxtadze", e.message.toString())

                    // The SMS quota for the project has been exceeded
                    // ...
                }
                // Show a message and update the UI
                // ...
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                mResendToken = token
                Log.d("baxtadze", "${mVerificationId},${mResendToken}")

            }
        }

    private fun checkVerificationCode() {
        binding.btnNext.setOnClickListener {
            if (binding.etPhoneNumber.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please enter OTP", Toast.LENGTH_SHORT).show()
            } else {
                verifyVerificationCode(binding.etPhoneNumber.text.toString())
            }
        }
    }


    private fun verifyVerificationCode(code: String) {
        try {
            //creating the credential
            val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)
            //signing the user
            signInWithPhoneAuthCredential(credential)
            Log.d("baxtadze", credential.smsCode.toString())

        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("baxtadze", e.localizedMessage.toString())

        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity(),
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser? = task.result.user
                        Log.d("baxtadze", user?.phoneNumber.toString())

                    } else {
                        var message = task.exception?.message.toString()
                        Log.d("baxtadze", message)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "wronggg monacmebi 187"
                        }
                    }
                })
    }
}


