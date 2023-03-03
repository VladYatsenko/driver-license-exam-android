package com.testdai.model

enum class Category {
    A1, A, B1, B, C1, C,
    D1, D, C1E, D1E,
    T, BE, CE, DE;

    val groupName: String
        get() = name.replace("1", "")

    companion object {
        fun typeOf(value: String?) = values().firstOrNull { it.name == value }

        /**
         * ['A']
         * ['B']
         * ['A', 'B']
         * ['C']
         * ['B', 'C']
         * ['A', 'B', 'C']
         * ['A', 'C']
         * ['D']
         * ['A', 'D']
         * ['B', 'D']
         * ['C', 'D']
         * ['T']
         * ['BE', 'CE']
         * ['BE', 'CE', 'A']
         * ['BE', 'CE', 'D']
         * ['DE']
         */
        val groups = listOf(
            setOf(A.name),
            setOf(B.name),
            setOf(A.name, B.name),
            setOf(C.name),
            setOf(B.name, C.name),
            setOf(A.name, B.name, C.name),
            setOf(A.name, C.name),
            setOf(D.name),
            setOf(A.name, D.name),
            setOf(B.name, D.name),
            setOf(C.name, D.name),
            setOf(T.name),
            setOf(BE.name, CE.name),
            setOf(BE.name, CE.name, A.name),
            setOf(BE.name, CE.name, D.name),
            setOf(DE.name),
        )
    }
}
