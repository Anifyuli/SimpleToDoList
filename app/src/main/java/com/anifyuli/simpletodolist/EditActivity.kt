package com.anifyuli.simpletodolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditActivity : AppCompatActivity() {

    private val api by lazy { RetrofitApi().endpoint }
    private val did by lazy { intent.getSerializableExtra("did") as ToDoModels.Data }
    private lateinit var editDid: EditText
    private lateinit var buttonEdit: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar!!.title = "Edit existing activity"
        setupView()
        setupListener()
    }

    private fun setupView() {
        editDid = findViewById(R.id.edit_did)
        buttonEdit = findViewById(R.id.button_edit)
        editDid.setText(did.did)
    }

    private fun setupListener() {
        buttonEdit.setOnClickListener{
            api.update(did.id!!, editDid.text.toString())
                .enqueue(object : Callback<SubmitModel>{
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

                    override fun onFailure(call: Call<SubmitModel>, t: Throwable) { }

                })
        }
    }
}
