package com.example.pokedex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import com.example.pokedex.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNav()

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun selectMenuOption(menuItem: MenuItem){
        menuItem.isChecked = true
        drawerLayout.closeDrawers()
    }

    private fun initNav(){
        val myNavHostFragment : NavHostFragment = fragment as NavHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        val graph_main = inflater.inflate(R.navigation.main_graph)
        val graph_ability = inflater.inflate(R.navigation.ability_graph)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item_pokedex -> myNavHostFragment.navController.graph = graph_main //Toast.makeText(applicationContext, "item 1", Toast.LENGTH_SHORT).show()
                R.id.item_abilyt -> myNavHostFragment.navController.graph = graph_ability //Toast.makeText(applicationContext, "item 2", Toast.LENGTH_SHORT).show()
                R.id.item3 -> Toast.makeText(applicationContext, "item 3", Toast.LENGTH_SHORT).show()
            }
            true
        }

//        navView.setNavigationItemSelectedListener { item ->
//            selectMenuOption(item)
//            true
//        }
    }
}