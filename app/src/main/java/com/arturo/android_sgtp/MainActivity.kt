package com.arturo.android_sgtp

import com.arturo.android_sgtp.data.model.Task
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arturo.android_sgtp.adapters.TaskAdapter
import com.arturo.android_sgtp.data.RetrofitServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.task_list)

        val retrofitService = RetrofitServiceFactory.makeRetrofitService()
        suspend fun getTasks(rut: String): List<Task>? {
            val response = retrofitService.getTasks("12345678-9").awaitResponse()
            if (response.status == 200) {
                val tasks = response.body()
                recyclerView.adapter = TaskAdapter(tasks)

            } else if (response.status == 404){
                var voidTask = Task("No hay tareas", "", "")
                recyclerView.adapter = TaskAdapter(listOf(voidTask))
            }
        }


        // Asignar un LayoutManager al RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar el relleno del RecyclerView para que coincida con el relleno del ConstraintLayout
        recyclerView.setPadding(resources.getDimensionPixelSize(R.dimen.activity_vertical_margin),
            resources.getDimensionPixelSize(R.dimen.activity_vertical_margin),
            resources.getDimensionPixelSize(R.dimen.activity_vertical_margin),
            resources.getDimensionPixelSize(R.dimen.activity_vertical_margin))

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}