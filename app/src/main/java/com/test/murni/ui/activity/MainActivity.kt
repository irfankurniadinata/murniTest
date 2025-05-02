package com.test.murni.ui.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.test.murni.R
import com.test.murni.core.BaseActivity
import com.test.murni.databinding.ActivityMainBinding
import com.test.murni.ui.dashboard.DashboardFragment
import com.test.murni.ui.home.HomeFragment
import com.test.murni.ui.notifications.NotificationsFragment
import com.test.murni.utils.K
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getResLayoutId(): Int = R.layout.activity_main

    private val viewModel by viewModel<MainViewModel>()

    private val homeMenu = listOf(
        R.id.navigation_home,
        R.id.navigation_dashboard,
        R.id.navigation_notifications
    )

    private val homeFragment = HomeFragment()
    private val dashboardFragment = DashboardFragment()
    private val notificationsFragment = NotificationsFragment()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.apply {
            data = viewModel

            navView.setOnNavigationItemSelectedListener { item ->
                onNavigationItemSelected(item.itemId)

                return@setOnNavigationItemSelectedListener true
            }
            navView.itemIconTintList = null
            setMainMenu(intent.getIntExtra(K.KEY_MAIN_MENU, 0))
        }
    }

    private fun setMainMenu(menuId: Int) {
        if (menuId in homeMenu) {
            binding.navView.selectedItemId = menuId
        } else {
            onNavigationItemSelected(R.id.navigation_home)
        }
    }

    var activeFragment: Fragment? = null
    private fun onNavigationItemSelected(menuId: Int) {
        var selectedFragment: Fragment? = null
        when (menuId) {
            R.id.navigation_home -> {
                selectedFragment = homeFragment
            }
            R.id.navigation_dashboard -> {
                selectedFragment = dashboardFragment
            }
            R.id.navigation_notifications -> {
                selectedFragment = notificationsFragment
            }
        }

        if (selectedFragment == null) return

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

        val current = activeFragment
        if (current != null && current.isAdded && current.isVisible) {
            ft.hide(current)
        }

        if (selectedFragment.isAdded) {
            ft.show(selectedFragment).commitNow()
        } else {
            try {
                val tag = getTag(menuId)
                val oldFragment = supportFragmentManager.findFragmentByTag(tag)
                if (oldFragment != null) ft.remove(oldFragment)
                ft.add(R.id.container, selectedFragment, tag).disallowAddToBackStack()
                    .commitNow()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        activeFragment = selectedFragment
    }

    private fun getTag(menuId: Int): String {
        return when (menuId) {
            R.id.navigation_home -> "home_fragment"
            R.id.navigation_dashboard -> "dashboard_fragment"
            R.id.navigation_notifications -> "notifications_fragment"
            else -> "fragment"
        }
    }
}