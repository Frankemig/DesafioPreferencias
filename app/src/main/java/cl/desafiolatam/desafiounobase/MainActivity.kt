package cl.desafiolatam.desafiounobase

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val filename ="package cl.desafiolatam.desafiounobase.MainActivity"

    lateinit var container: ConstraintLayout
    lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = getSharedPreferences(filename, Context.MODE_PRIVATE)
        container = findViewById(R.id.container)
        setUpListeners()
    }

    private fun setUpListeners() {
        login_button.setOnClickListener {
            if (name_input.text!!.isNotEmpty()) {
                val intent: Intent
                if (hasSeenWelcome()) {
                    intent = Intent(this, HomeActivity::class.java)
                } else {
                    saveUsers(name_input.toString())
                    intent = Intent(this, WelcomeActivity::class.java)
                }
                startActivity(intent)
            } else {
                Snackbar.make(container, "El nombre no puede estar vacío", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun hasSeenWelcome(): Boolean {
        val stringSet = preferences.getStringSet("Usuarios", mutableSetOf())
       return stringSet.contains(name_input.text.toString())
    }

    private fun saveUsers(userName:String){
        val setString = preferences.getStringSet("Usuarios", mutableSetOf())
        setString.add(userName)
      preferences.edit().putStringSet("Usuarios", setString).apply()
    }
}
