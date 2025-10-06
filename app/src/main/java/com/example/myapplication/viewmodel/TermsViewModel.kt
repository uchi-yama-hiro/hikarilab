package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * 利用規約画面のViewModel
 * 同意チェックボックスの状態を管理します。
 */
class TermsViewModel : ViewModel() {

    // 同意チェックボックスの状態を保持するStateFlow
    // _agreed.valueで値を更新し、agreed.valueで値を取得します。
    private val _agreed = MutableStateFlow(false)
    val agreed = _agreed.asStateFlow()

    /**
     * チェックボックスの状態を切り替えます。
     */
    fun toggleAgreed() {
        _agreed.update { !it }
    }
}
