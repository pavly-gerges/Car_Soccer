package com.scrappers.carsoccer.GameMenus

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import com.scrappers.carsoccer.GameMenus.GameClient.GameClient
import com.scrappers.carsoccer.JmeGame.GameDataNodes
import com.scrappers.carsoccer.JmeGame.GameStructure
import com.scrappers.carsoccer.LocalDataBase
import com.scrappers.carsoccer.R

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        SystemVisibilityUI(this).setGameMode()

        val localDataBase = LocalDataBase(this, "user.json")
        try {
            when {
                localDataBase.readData(2, "isRememberMe") as Boolean -> {
                    GameStructure.setPlayerAccount(localDataBase.readData(0, "name").toString())
                    startActivity(Intent(this, GameClient::class.java))
                    finish()
                }
            }
        }catch (e: NullPointerException){
            e.printStackTrace()
        }
        val signInBtn=findViewById<ImageView>(R.id.signIn)
        signInBtn.setOnClickListener {

            localDataBase.writeData((findViewById<EditText>(R.id.userName)).text.toString(),
                    ((findViewById<EditText>(R.id.userPassword))).text.toString(),
                    (findViewById<AppCompatCheckBox>(R.id.rememberMeCheck)).isChecked)

            val gameNodes= GameDataNodes()
            gameNodes.createAccount((findViewById<EditText>(R.id.userName)).text.toString(), (findViewById<EditText>(R.id.userPassword)).text.toString())

            startActivity(Intent(this, GameClient::class.java))
            finish()
        }
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            SystemVisibilityUI(this).setGameMode()
        }
    }
}