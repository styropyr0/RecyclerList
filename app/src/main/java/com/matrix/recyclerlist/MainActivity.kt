package com.matrix.recyclerlist

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.matrix.recycler_list.RecyclerList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dataList: MutableList<Data> = mutableListOf()
        for (i in 1..10) {
            dataList.add(Data("Title $i", "This is the content of $i"))
        }

        val recyclerList = findViewById<RecyclerList<Data>>(R.id.recyclerList)
        recyclerList.apply {
            setItems(dataList.toList(), R.layout.cell_layout) { view, item, index ->
                view.findViewById<TextView>(R.id.title).text = item.title
                view.findViewById<TextView>(R.id.content).text = item.content
                view.setOnClickListener {
                    recyclerList.removeItem(index)
                }
            }
        }
    }
}