package com.example.booksale

import android.content.Intent
import android.os.Bundle
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
    lateinit var Id: EditText
    lateinit var password:EditText
    lateinit var PasswordCheck:EditText
    lateinit var nickName:EditText
    lateinit var userName:EditText
    lateinit var phoneNum:EditText
    lateinit var Signup:Button
    lateinit var ChkBtn :Button
    lateinit var CheckBtn:Button
    private var dialog: AlertDialog? = null
    private var validate = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        Id = findViewById<EditText>(R.id.et_id)
        password = findViewById<EditText>(R.id.et_pass)
        PasswordCheck = findViewById<EditText>(R.id.et_passck)
        nickName = findViewById<EditText>(R.id.et_Nick)
        userName = findViewById<EditText>(R.id.et_name)
        phoneNum = findViewById<EditText>(R.id.et_number)
        Signup = findViewById<Button>(R.id.btn_register)

        //아이디 중복 체크
        ChkBtn = findViewById(R.id.validateButton)
        ChkBtn.setOnClickListener(View.OnClickListener{
            val IdCheck = Id.getText().toString()
            if(validate){
                return@OnClickListener
            }
            if(IdCheck == ""){
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@SignupActivity)
                dialog = builder.setMessage("아이디를 입력하세요").setPositiveButton("확인",null).create()
                dialog!!.show()
                return@OnClickListener
            }
            val responseListener: Response.Listener<String?> = Response.Listener<String?>{ response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val success = jsonResponse.getBoolean("success")
                    if(success){
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this@SignupActivity)
                        dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인",null).create()
                        dialog!!.show()
                        Id.setEnabled(false)//아이디고정
                        validate = true
                    } else{
                        val builder :AlertDialog.Builder = AlertDialog.Builder(this@SignupActivity)
                        dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인",null).create()
                        dialog!!.show()
                    }
                } catch (e:JSONException){
                    e.printStackTrace()
                }
            }
            val idcheckActivity = IdCheckActivity(IdCheck, responseListener)
            val queue = Volley.newRequestQueue(this@SignupActivity)
            queue.add(idcheckActivity)

        })


        //닉네임 중복 체크
        CheckBtn = findViewById(R.id.CheckBtn);
        CheckBtn.setOnClickListener(View.OnClickListener{
            val NicknameCk = nickName.getText().toString()
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
                        nickName.setEnabled(false)//닉네임고정
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



        //회원가입 버튼 이벤트
        Signup!!.setOnClickListener{
            val ID = Id!!.text.toString()
            val Password = password!!.text.toString()
            val pwck = PasswordCheck!!.text.toString()
            val NickName = nickName!!.text.toString()
            val UserName = userName!!.text.toString()
            val PhoneNum = phoneNum!!.text.toString()
            val responseListener: Response.Listener<String?> = Response.Listener<String?>{ response ->
            try{
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                if(success){
                    Toast.makeText(applicationContext,"회원가입이 성공적으로 처리되었습니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(applicationContext, "회원가입 요청처리 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    return@Listener
                }
            } catch (e:JSONException){
                e.printStackTrace()
                Toast.makeText(applicationContext,"예외 1", Toast.LENGTH_SHORT).show()
                return@Listener
            } catch (e:JSONException){
                e.printStackTrace()
            }
        }
            val signupRequestActivity = SignupRequestActivity(ID, Password, UserName,NickName, PhoneNum, responseListener)
            val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
            queue.add(signupRequestActivity)
        }
    }
}


