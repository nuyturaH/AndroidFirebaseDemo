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
import androidx.navigation.fragment.findNavController
import com.harutyun.androidfirebasedemo.databinding.FragmentSignUpBinding
import com.harutyun.androidfirebasedemo.presentation.NavigationCommand
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()

        observeState()

        observeNavigation()
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

    private fun observeNavigation() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpViewModel.navigation.collect { navigationCommand ->
                    handleNavigation(navigationCommand)
                }
            }
        }
    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> findNavController().navigate(navCommand.directions)
            is NavigationCommand.Back -> findNavController().navigateUp()
            is NavigationCommand.None -> {}
        }
    }


    private fun addListeners() {

        binding.btnSignUp.setOnClickListener {

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