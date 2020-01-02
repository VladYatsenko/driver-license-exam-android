package com.android.testdai.viewmodel

import androidx.lifecycle.ViewModel
import com.android.testdai.managers.ConnectionManager
import com.android.testdai.managers.SharedPreferencesManager
import javax.inject.Inject

class TestViewModel @Inject constructor(
        var sharedPreferencesManager: SharedPreferencesManager,
        var connectionDetector: ConnectionManager
) : ViewModel() {


}