package roshan.paturkar.com.smokerminimodel

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var user: FirebaseUser? = null
    var userId: String? = null
    var mAuthListner: FirebaseAuth.AuthStateListener? = null

    var progressDailog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "Login!"

        progressDailog = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()
        mAuthListner = FirebaseAuth.AuthStateListener {
                firebaseAuth: FirebaseAuth ->

            user = firebaseAuth.currentUser

            if (user != null){
                startActivity(Intent(this, DashboardActivity::class.java))
            }else{
                Toast.makeText(this, "User Not Signed In...!!!", Toast.LENGTH_LONG).show()
            }
        }

        createAccount.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        forgetPassword.setOnClickListener {
            var email: String = emailLoginEditText.text.toString().trim()

            if (email.isEmpty()){
                emailLoginEditText.setError("Enter email!")
            } else {
                progressDailog!!.setMessage("Sending request to server...")
                progressDailog!!.show()

                mAuth!!.sendPasswordResetEmail(email)
                    .addOnCompleteListener{

                            task ->

                        if (task.isSuccessful){
                            progressDailog!!.dismiss()

                            var data = AlertDialog.Builder(this)
                            with(data){
                                setTitle("Request Send!")
                                setMessage("Please check your mailbox and follow the instructions! \n\t\t\t\t\t       Thank You!")
                                setPositiveButton("OK"){
                                        dialog, which ->
                                    dialog.dismiss().also {
                                        startActivity(Intent(context, MainActivity::class.java))
                                        finish()
                                    }
                                }
                            }

                            data.show()

                        }else{
                            progressDailog!!.dismiss()
                            Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        loginButton.setOnClickListener {
            var email: String = emailLoginEditText.text.toString().trim()
            var password: String = passwordLoginEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()){
                loginUser(email, password)
            }else{
                if (TextUtils.isEmpty(email)){
                    emailLoginEditText.setError("Email required !")
                }
                if (TextUtils.isEmpty(password)){
                    passwordLoginEditText.setError("Password required !")
                }
            }
        }
    }

    private fun loginUser(email: String, password: String) {

        progressDailog!!.setMessage("Signing In...")
        progressDailog!!.show()

        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                    task: Task<AuthResult> ->

                if (task.isSuccessful){
                    progressDailog!!.dismiss()
                    var userName = email.split("@")[0] //split = roshan@gmail.com --> [roshan],[gmail.com]

                    var dashboardIntent = Intent(this, DashboardActivity::class.java)
                    dashboardIntent.putExtra("name", userName)
                    startActivity(dashboardIntent)
                    finish()
                }else{
                    progressDailog!!.dismiss()
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
                }

            }
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListner!!)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListner != null){
            mAuth!!.removeAuthStateListener(mAuthListner!!)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }
}
