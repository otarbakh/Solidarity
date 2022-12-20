package com.example.solidarity.ui.verification

import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.solidarity.common.BaseFragment
import com.example.solidarity.databinding.FragmentVerificationBinding
import com.example.solidarity.ui.otp_register.OtpRegisterFragmentDirections
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class VerificationFragment : BaseFragment<FragmentVerificationBinding>(FragmentVerificationBinding::inflate) {


    private lateinit var auth: FirebaseAuth
//    var mVerificationId: String? = args.verificationID

    var mVerificationId: String? = null
    var mResendToken: PhoneAuthProvider.ForceResendingToken? = null


    override fun viewCreated() {
        auth = Firebase.auth

    }

    override fun listeners() {
        checkVerificationCode()
    }


     val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                //Getting the code sent by SMS

                val code = credential.smsCode


                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code

                code?.let {verifyVerificationCode(it) }
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


     fun verifyVerificationCode(code: String) {
        try {
            //creating the credential
            val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)
            //signing the user
            signInWithPhoneAuthCredential(credential)
            Log.d("baxtadze", credential.smsCode.toString())

        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()

        }
    }

    private fun checkVerificationCode() {
        binding.btnNext.setOnClickListener {
            if (binding.etOtpCode.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please enter OTP", Toast.LENGTH_SHORT).show()
            } else {
                verifyVerificationCode(binding.etOtpCode.text.toString())
            }
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
                            message = "wrong monacmebi 187"
                        }
                    }
                })
    }

}