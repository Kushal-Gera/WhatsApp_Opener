package com.example.intentstuff

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hide.setOnClickListener {
            val p = packageManager
            val componentName = ComponentName(
                this,
                MainActivity::class.java
            )
            p.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            Toast.makeText(this, "Hidden", Toast.LENGTH_SHORT).show()
            finish()
        }

        val number = if (intent.action == Intent.ACTION_PROCESS_TEXT)
                intent?.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
            else if ((intent.action == Intent.ACTION_DIAL) || (intent.action == Intent.ACTION_VIEW))
                intent?.data?.schemeSpecificPart.toString()
            else
              return

        startWhatsApp(number.trim())


    }

    private fun startWhatsApp(number: String) {

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")

        var data = if (number[0] == '+')
            number.substring(1)
        else number

        data = if (data.length == 10)
            "91$data"
        else data

        intent.data = Uri.parse("https://wa.me/$data")

        if (packageManager.resolveActivity(intent, 0) != null)
            startActivity(intent)
        else
            Toast.makeText(this, "Please Install WhatsApp!!", Toast.LENGTH_SHORT).show()

        finish()

    }
}
