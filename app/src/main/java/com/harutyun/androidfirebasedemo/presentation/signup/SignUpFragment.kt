package com.harutyun.androidfirebasedemo.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.harutyun.androidfirebasedemo.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()

        observeState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpViewModel.uiState.collect { uiState ->
                    handleUiState(uiState)
                }
            }
        }
    }

    private fun handleUiState(uiState: SignUpUiState) {
        binding.apply {

            pbSignUp.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
            btnSignUp.visibility = if (uiState.isLoading) View.GONE else View.VISIBLE

            tilEmailSignUp.error = uiState.emailErrorMessage
            tilPasswordSignUp.error = uiState.passwordErrorMessage
        }
    }


    private fun addListeners() {

        binding.btnSignUp.setOnClickListener{

            signUpViewModel.signUpUser(
                binding.etEmailSignUp.text.toString(),
                binding.etPasswordSignUp.text.toString()
            )
        }

        binding.etEmailSignUp.doAfterTextChanged { binding.tilEmailSignUp.error = null }
        binding.etPasswordSignUp.doAfterTextChanged { binding.tilPasswordSignUp.error = null }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}