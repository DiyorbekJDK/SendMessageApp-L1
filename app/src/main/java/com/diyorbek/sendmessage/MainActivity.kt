package com.diyorbek.sendmessage

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var editMessage: EditText
    private lateinit var editPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        val btnCall: Button = findViewById(R.id.buttonCall)
        val btnSms: Button = findViewById(R.id.buttonSms)
        val btnWeb: Button = findViewById(R.id.button8)
        btnWeb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://youtube.com")
            startActivity(intent)
        }
        btnSms.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("sms:")
            intent.putExtra("address","+998${editPhone.text.toString().trim()}")
            intent.putExtra("sms_body",editMessage.text.toString().trim())
            startActivity(intent)
        }
        btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+998${editPhone.text.toString().trim()}")
            startActivity(intent)
        }

        editMessage = findViewById(R.id.editTextTextPersonName)
        editPhone = findViewById(R.id.editTextPhone)
        val btnSend: Button = findViewById(R.id.button)

        btnSend.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.SEND_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.SEND_SMS,android.Manifest.permission.CALL_PHONE),
                    100
                )
            } else {
                sendSms(editPhone.text.toString().trim(), editMessage.text.toString().trim())
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Ruhsat berildi", Toast.LENGTH_SHORT).show()
            sendSms(editPhone.text.toString().trim(), editMessage.text.toString().trim())
        } else {
            Toast.makeText(this, "Ruhsat Berilmadi!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendSms(number: String, message: String) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage("+998$number", null, message, null, null)
        Toast.makeText(this, "Xabar jo'natildi", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "GoodBye", Toast.LENGTH_SHORT).show()
    }
}