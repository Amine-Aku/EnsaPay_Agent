package com.impression.ensapayagent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.impression.ensapayagent.fragments.HomeFragment
import com.impression.ensapayagent.fragments.ProfileFragment
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceNavigationView
import com.luseen.spacenavigation.SpaceOnClickListener

class MainActivity : AppCompatActivity() {

    private lateinit var navView: SpaceNavigationView

    private lateinit var _acceuil: String
    private lateinit var _profil: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        bottomNavigation(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()




    }

    private fun init() {
        _acceuil = getString(R.string.acceuil)
        _profil = getString(R.string.profil)
    }

    private fun bottomNavigation(savedInstanceState: Bundle?) {
        navView = findViewById(R.id.space)
        navView.initWithSaveInstanceState(savedInstanceState)
        navView.addSpaceItem(SpaceItem(_acceuil, R.drawable.ic_home))
        navView.addSpaceItem(SpaceItem(_profil, R.drawable.ic_profile))

        navView.setSpaceOnClickListener(object: SpaceOnClickListener{
            override fun onCentreButtonClick() {
                startActivity(Intent(this@MainActivity, FormActivity::class.java))
//                navView.setCentreButtonSelectable(true)
            }

            override fun onItemReselected(itemIndex: Int, itemName: String?) {
//                Toast.makeText(this@MainActivity, "$itemIndex $itemName", Toast.LENGTH_SHORT).show()
            }

            override fun onItemClick(itemIndex: Int, itemName: String?) {
//                Toast.makeText(this@MainActivity, "$itemIndex $itemName", Toast.LENGTH_SHORT).show()
                var selectedFragment: Fragment? = null
                when(itemName){
                    _acceuil -> {
                        selectedFragment = HomeFragment()
                        title = getString(R.string.app_name)
                    }
                    _profil -> {
                        selectedFragment = ProfileFragment()
                        title = _profil
                    }
                }
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment!!).commit()
            }
        })


    }

//    keep selected item position and badge on device rotation
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        navView.onSaveInstanceState(outState)
    }


}
