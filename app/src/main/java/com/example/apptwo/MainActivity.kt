package com.example.apptwo



import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var CONTENT_URI = Uri.parse("content://com.demo.user.provider/users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("Range", "Recycle")
    fun onClickShowDetails(view: View?) {
        // inserting complete table details in this text field
        val resultView = findViewById<View>(R.id.res) as TextView

        try {
            contentResolver.query(CONTENT_URI, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val strBuild = StringBuilder()
                    do {
                        val id = cursor.getString(cursor.getColumnIndex("id"))
                        val name = cursor.getString(cursor.getColumnIndex("name"))
                        strBuild.append("\n$id-$name")
                    } while (cursor.moveToNext())
                    resultView.text = strBuild.toString().trim()
                } else {
                    resultView.text = "No Records Found"
                }
            } ?: run {
                resultView.text = "Error: Unable to access content provider"
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error querying content provider", e)
            resultView.text = "Error: ${e.message}"
        }
    }
}
