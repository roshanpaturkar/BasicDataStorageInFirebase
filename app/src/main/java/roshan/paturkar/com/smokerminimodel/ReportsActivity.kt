package roshan.paturkar.com.smokerminimodel

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_reports.*
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class ReportsActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var userId: String? = null
    var mCurrentUser: FirebaseUser? = null

    val db = FirebaseFirestore.getInstance()

    var data: String? = ""

    var progressDailog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)
        supportActionBar!!.title = "My Reports!"

        progressDailog = ProgressDialog(this)
        progressDailog!!.setMessage("Fetching Data....")
        progressDailog!!.show()

        mCurrentUser = FirebaseAuth.getInstance().currentUser
        userId = mCurrentUser!!.uid

        mAuth = FirebaseAuth.getInstance()

        db.collection(userId!!)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    data = document.data.get("report").toString() + "\n" + data
                    Log.d("Data Read >>> ", document.id + " => " + document.data)
                    reports.text = data
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Data Failed >>> ", "Error getting documents.", exception)
            }
        progressDailog!!.dismiss()
    }
}
