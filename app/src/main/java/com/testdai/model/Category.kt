package com.testdai.model

enum class Category {
    A1, B1, C1, D1,
    C1E, D1E,
    A, B, C, D, T,
    BE, CE, DE;

    val group: CategoryGroup
        get() = when (this) {
            A1, B1, C1, D1,
            A, B, C, D -> CategoryGroup.A_B_C_D
            C1E, BE, CE -> CategoryGroup.C1E_BE_CE
            D1E, DE -> CategoryGroup.D1E_DE
            T -> CategoryGroup.T
        }

    companion object {
        fun typeOf(value: String?) = values().firstOrNull { it.name == value }
    }
}

enum class CategoryGroup {
    A_B_C_D, C1E_BE_CE, D1E_DE, T
}