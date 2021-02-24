package com.skillbox.toolbarhomework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setNavigationOnClickListener {
            toast("Назад")
        }



        addButton.setOnClickListener {
            textView.visibility = View.VISIBLE
            toast("Текст добавлен")
            addButton.isEnabled = false
            clickedButton.isEnabled = true
        }


        clickedButton.setOnClickListener {
            textView.visibility = View.GONE
            toast("Текст удален")
            addButton.isEnabled = true
            clickedButton.isEnabled = false
        }

        toolbarActivity()

    }

    private fun toolbarActivity() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.param1 -> {
                    toast("Параметр 1")
                    true
                }
                R.id.param2 -> {
                    toast("Параметр 2")
                    true
                }
                R.id.setting -> {
                    toast("Настройки")
                    true
                }
                else -> false
            }
        }


        val search = toolbar.menu.findItem(R.id.actionSearch)

        search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                textSearch.text = "Привет"
                textSearch.visibility = View.VISIBLE
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                textSearch.text = "Пока"
                textSearch.visibility = View.VISIBLE
                Handler().postDelayed({
                    textSearch.text = ""
                }, 1000)
                return true
            }

        })
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}