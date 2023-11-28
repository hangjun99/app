package com.example.booksale

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    lateinit var userID: EditText
    lateinit var userPassword: EditText
    lateinit var Login: Button
    lateinit var Signup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userID = findViewById<EditText>(R.id.Put_ID)
        userPassword = findViewById<EditText>(R.id.Put_Pass)
        Login = findViewById<Button>(R.id.Login_btn)
        Signup = findViewById<Button>(R.id.Register_btn)

        //로그인 버튼 이벤트
        Login.setOnClickListener(View.OnClickListener {
            val ID = userID.getText().toString()
            val Password = userPassword.getText().toString()
            val responseListener =
                Response.Listener<String?> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success) {
                            val msg = jsonObject.getString("ID")
                            Toast.makeText(
                                applicationContext,
                                "로그인 성공. ID :$msg",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
                            return@Listener
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(applicationContext, "예외 1", Toast.LENGTH_SHORT).show()
                        return@Listener
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            val loginRequestActivity = LoginRequestActivity(ID, Password, responseListener)
            val queue = Volley.newRequestQueue(applicationContext)
            queue.add(loginRequestActivity)
        })


        //회원가입 버튼 이벤트
        Signup.setOnClickListener(View.OnClickListener {
            try {
                val intent = Intent(applicationContext, SignupActivity::class.java)
                startActivity(intent)
            }   catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "예외 발생: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}