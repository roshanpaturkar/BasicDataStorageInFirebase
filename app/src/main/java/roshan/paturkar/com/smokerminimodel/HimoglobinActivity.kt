package roshan.paturkar.com.smokerminimodel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_himoglobin.*

class HimoglobinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_himoglobin)
        supportActionBar!!.title = "Hemoglobin!"

        hemoglobinCheckButton.setOnClickListener {
            var status = check()

            hemoglobinStatus.text = status
        }

    }
    private fun check(): String{
        var he = hemoglobinEditText.text.toString().trim()

        if (he.isNotEmpty()) {
            var status = Support.checkHemoglobine(he)

            return status
        } else {
            Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_LONG).show()
            return ""
        }
    }
}
