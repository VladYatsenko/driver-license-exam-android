package com.android.testdai.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.testdai.application.TestDaiApplication
import com.android.testdai.di.components.ApplicationComponent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    val applicationComponent: ApplicationComponent
        get() = (application as TestDaiApplication).applicationComponent

    lateinit var compositeDisposable: CompositeDisposable

    private var currentFragment: Fragment? = null
    private var previousFragment: Fragment? = null
    private var fragmentManager: FragmentManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        compositeDisposable = CompositeDisposable()
    }

    protected fun showFragment(fragmentsContainerId: Int, fragment: Fragment, needToRefreshBackStack: Boolean, withDetachingPrevious: Boolean) {
        val backStackFragmentsCount = supportFragmentManager.backStackEntryCount
        if (needToRefreshBackStack) {
            for (i in backStackFragmentsCount - 1 downTo 0) {
                val backStackId = supportFragmentManager.getBackStackEntryAt(i).id
                supportFragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }

        previousFragment = currentFragment
        currentFragment = fragment
        val fragmentTag = fragment.hashCode().toString()
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (previousFragment != null) {
            fragmentManager?.findFragmentById(fragmentsContainerId)?.let {
                fragmentTransaction?.hide(it)
            }
            if (withDetachingPrevious) {
                previousFragment?.let {
                    fragmentTransaction?.detach(it)
                }
            }
        }

        if (fragmentManager != null && fragmentManager?.isStateSaved != true){
            fragmentTransaction?.add(fragmentsContainerId, currentFragment!!, fragmentTag)
                    ?.addToBackStack(fragmentTag)
                    ?.commit()
        }
    }

    abstract fun inject()
}