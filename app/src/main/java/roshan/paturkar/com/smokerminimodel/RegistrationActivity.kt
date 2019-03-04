package roshan.paturkar.com.smokerminimodel

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var mDatabaseRef: DatabaseReference? = null

    var progressDailog: ProgressDialog? = null

    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var DOB: String? = null
    var mobileNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar!!.title = "Register!"

        mAuth = FirebaseAuth.getInstance()
        progressDailog = ProgressDialog(this)

        registerButton.setOnClickListener {

            firstName = firstNameEditText.text.toString().trim()
            lastName = lastNameEditText.text.toString().trim()
            email = emailRegisterEditText.text.toString().trim()
            password = passwordEditText.text.toString().trim()
            confirmPassword = rePasswordEditText.text.toString().trim()
            DOB = dobEditText.text.toString().trim()
            mobileNumber = mobileNumberEditText.text.toString().trim()

            if (isDataIsNotEmpty()) {
                if (checkEmail(email!!) && checkNumber(mobileNumber!!) && checkPassword(password!!, confirmPassword!!)) {
                    registerUser(email!!, password!!)
                }
            }
        }
    }

    private fun registerUser(registerEmail: String, registerPassward: String) {
        progressDailog!!.setMessage("Signing Up...")
        progressDailog!!.show()
        mAuth!!.createUserWithEmailAndPassword(registerEmail, registerPassward)
            .addOnCompleteListener {
                    task: Task<AuthResult> ->

                if (task.isSuccessful) {
                    var currentUser = mAuth!!.currentUser
                    var userID = currentUser!!.uid

                    mDatabaseRef = FirebaseDatabase.getInstance().reference
                        .child("GHRCE").child("Users").child(userID)

                    var userObject = HashMap<String, String> ()
                    userObject.put("firstName", firstName!!)
                    userObject.put("lastName", lastName!!)
                    userObject.put("email", registerEmail!!)
                    userObject.put("DOB", DOB!!)
                    userObject.put("mobile", mobileNumber!!)

                    mDatabaseRef!!.setValue(userObject).addOnCompleteListener {
                            task: Task<Void> ->

                        if (task.isSuccessful){
                            progressDailog!!.dismiss()
                            Toast.makeText(this, "$firstName Successfully Sign Up!", Toast.LENGTH_LONG).show()

                            var dashboardIntent = Intent(this, DashboardActivity::class.java)
                            dashboardIntent.putExtra("name", firstName)
                            startActivity(dashboardIntent)
                            finish()
                        } else {
                            progressDailog!!.dismiss()
                            Toast.makeText(this, "$firstName Failed to save the data!", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    progressDailog!!.dismiss()
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }
    }


    private fun isDataIsNotEmpty(): Boolean{
        if (firstName!!.isNotEmpty()
            && lastName!!.isNotEmpty()
            && email!!.isNotEmpty()
            && password!!.isNotEmpty()
            && confirmPassword!!.isNotEmpty()
            && DOB!!.isNotEmpty()
            && mobileNumber!!.isNotEmpty()){
            return true
        } else {
            if (firstName!!.isEmpty()) firstNameEditText.setError("First name required!")
            if (lastName!!.isEmpty()) lastNameEditText.setError("Last name required!")
            if (email!!.isEmpty()) emailRegisterEditText.setError("Email required!")
            if (password!!.isEmpty()) passwordEditText.setError("Password required!")
            if (confirmPassword!!.isEmpty()) rePasswordEditText.setError("Re-enter password!")
            if (DOB!!.isEmpty()) dobEditText.setError("DOB is required!")
            if (mobileNumber!!.isEmpty()) mobileNumberEditText.setError("Mobile number required!")

            return false
        }
    }

    private fun checkPassword(passwd: String, cnpasswd: String): Boolean{
        if (TextUtils.equals(passwd, cnpasswd)) {
            return true
        }
        rePasswordEditText.setError("Password not match!")
        return false
    }

    private fun checkEmail(email: String): Boolean {
        val valid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (valid) {
            return true
        } else {
            emailRegisterEditText.setError("Email not valid!")
            return false
        }
    }

    private fun checkNumber(number: String): Boolean {
        if (number.length == 10) {
            return true
        } else {
            mobileNumberEditText.setError("Number not valid!")
            return false
        }
    }
}
