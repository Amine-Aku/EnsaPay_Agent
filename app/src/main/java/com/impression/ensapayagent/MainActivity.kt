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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()


    }

    private fun bottomNavigation(savedInstanceState: Bundle?) {
        navView = findViewById(R.id.space)
        navView.initWithSaveInstanceState(savedInstanceState)
        navView.addSpaceItem(SpaceItem("Acceuil", R.drawable.ic_home))
        navView.addSpaceItem(SpaceItem("Profil", R.drawable.ic_profile))

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
                    "Acceuil" -> selectedFragment = HomeFragment()
                    "Profil" -> selectedFragment = ProfileFragment()
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
