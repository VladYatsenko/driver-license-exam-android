package com.android.testdai.model

import com.google.gson.annotations.SerializedName


class GroupEntity constructor(
        var groupName: GroupEnum?,
        var isEnabled: Boolean = false
)