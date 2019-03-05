package roshan.paturkar.com.smokerminimodel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_blood_glucose.*

class BloodGlucoseActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_glucose)
        supportActionBar!!.title = "Blood Glucose!"

        checkSugarButton.setOnClickListener {
            var status = check()

            glStatus.text = status
        }

    }

    private fun check(): String{
        var fa = fastingEditText.text.toString().trim()
        var po = postMealEditText.text.toString().trim()

        if (fa.isNotEmpty() && po.isNotEmpty()) {
            var faStatus = Support.checkGlF(fa)
            var poStatus = Support.checkGlP(po)

            var status = faStatus + "\n" + poStatus

            return status
        } else {
            Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_LONG).show()
            return ""
        }
    }
}
