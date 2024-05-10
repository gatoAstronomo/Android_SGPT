package com.arturo.android_sgtp

import com.arturo.android_sgtp.data.model.Task
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arturo.android_sgtp.adapters.TaskAdapter
import com.arturo.android_sgtp.data.RetrofitServiceFactory
import com.arturo.android_sgtp.data.TaskRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.task_list)

        val taskRepository = TaskRepository.getInstance()
        lifecycleScope.launch {
            val taskList = taskRepository.getTasks(123456789)
            recyclerView.adapter = TaskAdapter(taskList)
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