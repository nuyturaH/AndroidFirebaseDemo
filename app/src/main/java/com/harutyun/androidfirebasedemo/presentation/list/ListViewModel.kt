package com.harutyun.androidfirebasedemo.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harutyun.domain.models.Item
import com.harutyun.domain.usecases.AddItemRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel@Inject constructor(
    private val addItemRemoteUseCase: AddItemRemoteUseCase
) : ViewModel() {

    fun addItemRemote(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            addItemRemoteUseCase(item)
        }
    }
}