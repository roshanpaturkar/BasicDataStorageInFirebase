package roshan.paturkar.com.smokerminimodel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calcium.*
import kotlinx.android.synthetic.main.activity_himoglobin.*

class CalciumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcium)
        supportActionBar!!.title = "Calcium!"

        calciumCheckButton.setOnClickListener {
            var status = check()

            calciumStatus.text = status
        }

    }
    private fun check(): String{
        var cl = calciumEditText.text.toString().trim()

        if (cl.isNotEmpty()) {
            var status = Support.checkCalcium(cl)

            return status
        } else {
            Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_LONG).show()
            return ""
        }
    }
}
