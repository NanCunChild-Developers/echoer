package com.nancunchild.echoer.activities

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.nancunchild.echoer.fragments.ChatScreen
import com.nancunchild.echoer.utils.Message
import java.io.IOException
import java.io.OutputStream

class ChatActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var deviceAddress = intent.getStringExtra("deviceAddress")
        var deviceName = intent.getStringExtra("deviceName")
        var isDarkMode = intent.getBooleanExtra("isDarkMode", false)

        setContent {
            ChatScreen(
                deviceAddress = deviceAddress,
                deviceName = deviceName,
                isDarkMode = isDarkMode,
                messageListener = this // 将当前Activity作为MessageListener传递给ChatScreen
            )
        }
    }


    interface MessageListener {
        fun sendMessage(deviceAddress: String, message: Message)
    }

    fun sendMessage(deviceAddress: String, message: Message) {
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        val bluetoothDevice: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)
        var bluetoothSocket: BluetoothSocket? = null
        var outputStream: OutputStream? = null
        var MY_UUID = message.id
        Log.d("ChatActivity", "准备开始发送消息")

        try {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            bluetoothSocket = bluetoothDevice?.createRfcommSocketToServiceRecord(MY_UUID)
            bluetoothSocket?.connect()
            outputStream = bluetoothSocket?.outputStream

            val messageBytes = message.content.toByteArray()
            outputStream?.write(messageBytes)

            // 添加日志消息
            Log.d("ChatActivity", "消息已发送: $message")
        } catch (e: IOException) {
            // 发送消息失败
            e.printStackTrace()
            Log.d("ChatActivity", "消息发送失败")
        } finally {
            try {
                outputStream?.close()
                bluetoothSocket?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}