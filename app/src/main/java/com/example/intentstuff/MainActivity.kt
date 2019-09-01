package com.example.intentstuff

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val number = if (intent.action == Intent.ACTION_PROCESS_TEXT)
                            intent?.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()

                    else if ((intent.action == Intent.ACTION_DIAL) || (intent.action == Intent.ACTION_VIEW))
                            intent?.data?.schemeSpecificPart.toString()

                    else "8588910153"

        startWhatsApp(number.trim())

    }

    private fun startWhatsApp(number : String) {

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")

        var data = if (number[0] == '+')
                        number.substring(1)
                   else number

        data = if (data.length == 10)
                    "91$data"
                else data

        intent.data = Uri.parse("https://wa.me/$data")

        if (packageManager.resolveActivity(intent,0) != null)
            startActivity(intent)

        else Toast.makeText(this, "Please Install WhatsApp!!", Toast.LENGTH_SHORT).show()
        finish()

    }
}
