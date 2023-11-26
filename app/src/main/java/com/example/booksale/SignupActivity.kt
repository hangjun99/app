package com.example.booksale

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class SignupActivity : AppCompatActivity() {
    lateinit var ID: EditText
    lateinit var Password:EditText
    lateinit var PasswordCheck:EditText
    lateinit var NickName:EditText
    lateinit var UserName:EditText
    lateinit var PhoneNum:EditText
    lateinit var Signup:Button
    lateinit var CheckBtn:Button
    private var dialog: AlertDialog? =null
    private var validate = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        ID = findViewById<EditText>(R.id.et_id)
        Password = findViewById<EditText>(R.id.et_pass)
        PasswordCheck = findViewById<EditText>(R.id.et_passck)
        NickName = findViewById<EditText>(R.id.et_Nick)
        UserName = findViewById<EditText>(R.id.et_name)
        PhoneNum = findViewById<EditText>(R.id.et_number)
        Signup = findViewById<Button>(R.id.btn_register)

        //닉네임 중복 체크
        CheckBtn = findViewById(R.id.CheckBtn);
        CheckBtn.setOnClickListener(View.OnClickListener{
            val NicknameCk = NickName.getText().toString()
            if(validate){
                return@OnClickListener
            }
            if(NicknameCk == ""){
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@SignupActivity)
                dialog = builder.setMessage("닉네임을 입력하세요").setPositiveButton("확인",null).create()
                dialog!!.show()
                return@OnClickListener
            }
            val responseListener: Response.Listener<String?> = Response.Listener<String?>{ response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val success = jsonResponse.getBoolean("success")
                    if(success){
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this@SignupActivity)
                        dialog = builder.setMessage("사용할 수 있는 닉네임입니다.").setPositiveButton("확인",null).create()
                        dialog!!.show()
                        NickName.setEnabled(false)//닉네임고정
                        validate = true
                    } else{
                        val builder :AlertDialog.Builder = AlertDialog.Builder(this@SignupActivity)
                        dialog = builder.setMessage("이미 존재하는 닉네임입니다.").setNegativeButton("확인",null).create()
                        dialog!!.show()
                    }
                } catch (e:JSONException){
                    e.printStackTrace()
                }
            }
            val nicknameCheckActivity = NicknameCheckActivity(NicknameCk, responseListener)
            val queue = Volley.newRequestQueue(this@SignupActivity)
            queue.add(nicknameCheckActivity)
        })

        //비밀번호 일치 여부
        PasswordCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // no-op
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // no-op
            }

            override fun afterTextChanged(s: Editable?) {
                val password = Password.text.toString()
                val confirmPassword = s.toString()

                // 비밀번호가 일치하지 않으면 회원가입 버튼 비활성화
                Signup.isEnabled = password == confirmPassword
            }
        })

        //회원가입 버튼 이벤트
        Signup.setOnClickListener{
            val id = ID.text.toString()
            val pw = Password.text.toString()
            val pwck = PasswordCheck.text.toString()
            val nickname = NickName.text.toString()
            val username = UserName.text.toString()
            val phonenum = PhoneNum.text.toString()
            val responseListener: Response.Listener<String> = Response.Listener<String> { response ->
                Log.i("AppLog", "서버 응답: $response")
                try {
                    val jsonObject = JSONObject(response)
                    // 여기에서 JSON 파싱을 수행하고, 필요한 정보를 가져오세요.
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("SignupActivity", "JSON 파싱 오류: ${e.message}")
                    Toast.makeText(applicationContext, "서버 응답 처리 중 오류가 발생했습니다. (JSON 파싱 오류)", Toast.LENGTH_SHORT).show()
                }
            }
            val signupRequestActivity = SignupRequestActivity(id, pw, username, nickname, phonenum,
                Response.Listener<String?> { response ->
                    // 서버 응답을 로그에 출력
                    Log.d("SignupActivity", "서버 응답: $response")

                    try {
                        // JSON 파싱
                        val jsonObject = JSONObject(response)

                        // 여기에서 JSON 파싱을 수행하고, 필요한 정보를 가져오세요.
                        val success = jsonObject.optBoolean("success", false)
                        val errorMessage = jsonObject.optString("message", "")
                        if (success) {
                            // 성공적인 응답 처리
                            Toast.makeText(applicationContext, "회원가입이 성공적으로 처리되었습니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            // 실패 시 응답 메시지를 확인하여 디버깅
                            Log.e("SignupActivity", "회원가입 실패: $errorMessage")
                            Toast.makeText(applicationContext, "회원가입 실패: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        // JSON 파싱 오류 발생 시
                        e.printStackTrace()
                        Log.e("SignupActivity", "JSON 파싱 오류: ${e.message}")
                        Toast.makeText(applicationContext, "서버 응답 처리 중 오류가 발생했습니다. (JSON 파싱 오류)", Toast.LENGTH_SHORT).show()
                    } catch (e: JSONException) {
                        // JSON 파싱 오류 발생 시
                        e.printStackTrace()
                        Log.e("SignupActivity", "JSON 파싱 오류: ${e.message}")
                        Toast.makeText(applicationContext, "서버 응답 처리 중 오류가 발생했습니다. (JSON 파싱 오류)", Toast.LENGTH_SHORT).show()
                    }
                })
            val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
            queue.add(signupRequestActivity)
        }
    }
}