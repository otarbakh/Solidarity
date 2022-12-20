package com.example.solidarity.ui.otp_register


import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.solidarity.common.BaseFragment
import com.example.solidarity.databinding.FragmentOtpRegisterBinding
import com.example.solidarity.ui.verification.VerificationFragment
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


    val verificationFragment: VerificationFragment by lazy { VerificationFragment() }


    private lateinit var auth: FirebaseAuth

    private var selectedCountryCode = ""

    override fun viewCreated() {

        auth = Firebase.auth
        setupSpinner()
    }

    override fun listeners() {
        sendOtp()
        changeColours()
        checkVisibility()
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

    private fun sendOtp() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+995551585021") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(verificationFragment.mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("baxtadze", "gaigzavna kodi")

    }




}


