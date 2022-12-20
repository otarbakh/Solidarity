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




    override fun viewCreated() {


    }

    override fun listeners() {

    }


















}