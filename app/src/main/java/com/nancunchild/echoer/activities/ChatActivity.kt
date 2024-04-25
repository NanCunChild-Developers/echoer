package com.nancunchild.echoer.activities

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nancunchild.echoer.fragments.ChatScreen
import com.nancunchild.echoer.utils.Message
import com.nancunchild.echoer.utils.PermissionManager
import java.io.IOException
import java.io.OutputStream

class ChatActivity : ComponentActivity() {
    private lateinit var permissionManager: PermissionManager
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
        Log.d("ChatActivity", "消息发送 - 准备开始")

        try {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // 已经具有蓝牙连接权限，可以执行相关操作
                bluetoothSocket = bluetoothDevice?.createRfcommSocketToServiceRecord(MY_UUID)
                bluetoothSocket?.connect()
                outputStream = bluetoothSocket?.outputStream

                val messageBytes = message.content.toByteArray()
                outputStream?.write(messageBytes)

                // 添加日志消息
                Log.d("ChatActivity", "消息发送 - 成功: $message")
            } else {
                // 没有蓝牙连接权限，可能是因为在 MainActivity 中未授予该权限
                // 在此处根据需要进行处理，例如显示一个提示消息或禁用相关功能
                Toast.makeText(this, "没有蓝牙连接权限", Toast.LENGTH_SHORT).show()
                Log.d("ChatActivity", "消息发送 - 失败 - 没有权限")
            }

        } catch (e: IOException) {
            // 发送消息失败
            e.printStackTrace()
            Log.d("ChatActivity", "消息发送 - 失败 - IOException")
        } finally {
            Log.d("ChatActivity", "消息发送 - 善后")
            try {
                outputStream?.close()
                bluetoothSocket?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}