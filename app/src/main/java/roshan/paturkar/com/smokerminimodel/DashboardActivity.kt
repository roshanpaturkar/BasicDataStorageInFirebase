package roshan.paturkar.com.smokerminimodel

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import kotlinx.android.synthetic.main.nav_header_dashboard.*

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Dashboard!"

        setDrawerData()

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //-----------------------------------------------------------------------------------------------
        //                                  Working Area
        //-----------------------------------------------------------------------------------------------

        bpButton.setOnClickListener {
            startActivity(Intent(this, BloodPressureActivity::class.java))
        }

        bgButton.setOnClickListener {
            startActivity(Intent(this, BloodGlucoseActivity::class.java))
        }

        hemoButton.setOnClickListener {
            startActivity(Intent(this, HimoglobinActivity::class.java))
        }

        calButton.setOnClickListener {
            startActivity(Intent(this, CalciumActivity::class.java))
        }

        //-----------------------------------------------------------------------------------------------

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.reports -> {
                startActivity(Intent(this, ReportsActivity::class.java))
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    //---------------------------------------------------------------------------------------
    //                          User Define Function
    //---------------------------------------------------------------------------------------

    private fun setDrawerData() {
        var mCurrentUser: FirebaseUser? = null
        var userId: String? = null

        mCurrentUser = FirebaseAuth.getInstance().currentUser
        userId = mCurrentUser!!.uid

        var userName = FirebaseDatabase.getInstance().reference.child("GHRCE").child("Users").child(userId)

        userName!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var fname = dataSnapshot!!.child("firstName").value.toString().capitalize()
                var lname = dataSnapshot!!.child("lastName").value.toString().capitalize()
                var email = dataSnapshot!!.child("email").value.toString()

                navigationName.text = "$fname $lname"
                navigationEmail.text = email
            }
        })

    }

    //---------------------------------------------------------------------------------------
}
