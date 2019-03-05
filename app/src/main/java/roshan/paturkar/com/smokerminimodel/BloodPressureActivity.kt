package roshan.paturkar.com.smokerminimodel

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_blood_pressure.*

class BloodPressureActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var mDatabaseRef: DatabaseReference? = null

    var progressDailog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_pressure)
        supportActionBar!!.title = "Blood Pressure!"

        mAuth = FirebaseAuth.getInstance()
        progressDailog = ProgressDialog(this)

        checkSugarButton.setOnClickListener {
            var status = check()

            if (!status.equals("")) {
                bpStatus.text = status
            } else {
                Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_LONG).show()
            }
        }

        addBpToReport.setOnClickListener {
//            val currentUser = mAuth!!.currentUser
//            val userID = currentUser!!.uid
//            val status = check()
//            var report: String? = null
//
//            if (!status.equals("")){
//                Toast.makeText(this, "IN", Toast.LENGTH_LONG).show()
//                var userName = FirebaseDatabase.getInstance().reference.child("GHRCE").child("Reports").child(userID)
//
//                userName!!.addValueEventListener(object : ValueEventListener {
//                    override fun onCancelled(p0: DatabaseError?) {
//                        Log.d("NULL DATA >>>", p0.toString())
//                    }
//
//
//
//                    override fun onDataChange(dataSnapshot: DataSnapshot?) {
//                        report = dataSnapshot!!.child("report").value.toString()
//
//                        userName.child("report").setValue(status)
//
//                        if (report.equals("null")){
//                            userName.child("report").setValue(status)
//                        } else {
//                            //userName.child("report").setValue(status + "\n" + report)
//                        }
//                    }
//                })
//            } else {
//                Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_LONG).show()
//            }
            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_LONG).show()
        }
    }


    private fun check(): String{
        var sy = fastingEditText.text.toString().trim()
        var di = postMealEditText.text.toString().trim()

        if (sy.isNotEmpty() && di.isNotEmpty()) {
            var status = Support.checkBp(sy, di)
            return status
        } else {
            return ""
        }
    }
}
