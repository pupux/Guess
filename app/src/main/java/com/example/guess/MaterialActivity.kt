package com.example.guess

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.guess.databinding.ActivityMaterialBinding
import kotlinx.android.synthetic.main.content_material.*



class MaterialActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMaterialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        Log.d(TAG, "secret:"+secretNumber.secret)

        binding.fab.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle("Replay game")
                .setMessage("Are you sure?")
                .setPositiveButton(getString(R.string.ok),{Dialog,which ->
                    secretNumber.reset()
                    counter.setText(secretNumber.count.toString() +"次")
                    number.setText("")

                })
                .setNeutralButton("Cancel",null)
                .show()
        }
        counter.setText(secretNumber.count.toString()+"次")
    }
    fun check(view : View) {
        val n = number.text.toString().toInt()
        println("number: $n")
        Log.d(TAG, "number:\t" + n)
        val diff = secretNumber.validate(n)
        var message = getString(R.string.bingo)
        if(diff < 0){
            message = getString(R.string.bigger)
        }else if (diff > 0){
            message = getString(R.string.smaller)
        }else if (diff ==0 && secretNumber.count < 3){
            message = "Excellent! The number is ${n}"
            secretNumber.reset()
        }else {
            secretNumber.reset()
        }
        counter.setText(secretNumber.count.toString()+"次")
        number.setText("")
//        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok),null)
            .show()
    }
}