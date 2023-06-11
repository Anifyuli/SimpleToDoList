package com.anifyuli.simpletodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val api by lazy { RetrofitApi().endpoint }
    private lateinit var toDoAdapter: ToDoAdapter
    private lateinit var listToDo: RecyclerView
    private lateinit var fabCreate: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupList()
        setupView()
        setupListener()

    }

    private fun setupList() {
        listToDo = findViewById(R.id.list_todo)
        toDoAdapter = ToDoAdapter(arrayListOf(), object : ToDoAdapter.OnAdapterListener {
            override fun onUpdate(did: ToDoModels.Data) {
                startActivity(Intent(this@MainActivity, EditActivity::class.java)
                    .putExtra("did", did)
                )
            }

            override fun onDelete(did: ToDoModels.Data) {
                api.delete(did.id!!)
                    .enqueue(object : Callback<SubmitModel>{
                        override fun onResponse(
                            call: Call<SubmitModel>,
                            response: Response<SubmitModel>
                        ) {
                            if (response.isSuccessful) {
                                val submit = response.body()
                                Toast.makeText(applicationContext,submit!!.message, Toast.LENGTH_SHORT).show()
                                getToDo()
                            }
                        }

                        override fun onFailure(call: Call<SubmitModel>, t: Throwable) { }

                    })
            }

        })
        listToDo.adapter = toDoAdapter
    }

    override fun onStart() {
        super.onStart()
        getToDo()
    }

    private fun setupView(){
        listToDo = findViewById(R.id.list_todo)
        fabCreate = findViewById(R.id.fab_create)
    }

    private fun setupListener(){
        fabCreate.setOnClickListener{
            startActivity(Intent(this,CreateActivity::class.java))
        }
    }

    private fun getToDo() {
        api.data().enqueue(object : Callback<ToDoModels> {
            override fun onResponse(call: Call<ToDoModels>, response: Response<ToDoModels>) {
                if (response.isSuccessful) {
                    val listData = response.body()!!.todo
                    toDoAdapter.setData(listData)
//                    listData.forEach {
//                        Log.e("MainActivity",  "did ${it.did}")
//                    }
                }
            }

            override fun onFailure(call: Call<ToDoModels>, t: Throwable) {
                Log.e("MainActivity", t.toString())
            }

        })
    }
}