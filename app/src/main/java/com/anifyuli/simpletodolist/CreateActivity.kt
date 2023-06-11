package com.anifyuli.simpletodolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity : AppCompatActivity() {

    private val api by lazy { RetrofitApi().endpoint }
    private lateinit var editDid: EditText
    private lateinit var buttonCreate: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        supportActionBar!!.title = "Add a new activity"
        setupView()
        setupListener()
    }

    private fun setupView() {
        editDid = findViewById(R.id.edit_did)
        buttonCreate = findViewById(R.id.button_create)
    }

    private fun setupListener() {
        buttonCreate.setOnClickListener{
            if (editDid.text.toString().isNotEmpty()) {
                Log.e("",editDid.text.toString())
                api.create( editDid.text.toString() )
                    .enqueue(object : Callback<SubmitModel> {
                        override fun onResponse(
                            call: Call<SubmitModel>,
                            response: Response<SubmitModel>
                        ) {
                            if (response.isSuccessful) {
                                val submit = response.body()
                                Toast.makeText(applicationContext,submit!!.message, Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<SubmitModel>, t: Throwable) {

                        }

                    })
            } else {
                Toast.makeText(applicationContext,"Don't leave blank", Toast.LENGTH_SHORT).show()
            }
        }
    }
}