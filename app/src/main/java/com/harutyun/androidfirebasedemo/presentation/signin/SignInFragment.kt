package com.harutyun.androidfirebasedemo.presentation.signin

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
import com.harutyun.androidfirebasedemo.databinding.FragmentSignInBinding
import com.harutyun.androidfirebasedemo.presentation.navigation.NavigationCommand
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val signInViewModel: SignInViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()

        observeState()

        observeNavigation()
    }

    private fun addListeners() {

        binding.btnSignIn.setOnClickListener {
            signInViewModel.signInUser(
                binding.etEmailSignIn.text.toString(),
                binding.etPasswordSignIn.text.toString()
            )
        }

        binding.btnSignUpSignIn.setOnClickListener {
            signInViewModel.goToSignUpFragment()
        }


        binding.etEmailSignIn.doAfterTextChanged { binding.tilEmailSignIn.error = null }
        binding.etPasswordSignIn.doAfterTextChanged { binding.tilPasswordSignIn.error = null }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signInViewModel.uiState.collect { uiState ->
                    handleUiState(uiState)
                }
            }
        }
    }

    private fun handleUiState(uiState: SignInUiState) {
        binding.apply {

            pbSignIn.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
            btnSignIn.visibility = if (uiState.isLoading) View.INVISIBLE else View.VISIBLE

            if (uiState.emailErrorMessageId != 0)
                tilEmailSignIn.error = getString(uiState.emailErrorMessageId)

            if (uiState.passwordErrorMessageId != 0)
                tilPasswordSignIn.error = getString(uiState.passwordErrorMessageId)

            tvErrorSignIn.text =
                if (uiState.errorMessageId != 0) getString(uiState.errorMessageId)
                else uiState.errorMessage.ifEmpty { "" }
        }
    }

    private fun observeNavigation() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signInViewModel.navigation.collect { navigationCommand ->
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
        signInViewModel.navigationClear()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}