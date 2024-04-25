package com.nancunchild.echoer.activities

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
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
import java.util.UUID

class ChatActivity : ComponentActivity() {
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bluetoothDevice: BluetoothDevice
    private lateinit var bluetoothSocket: BluetoothSocket
    private lateinit var outputStream: OutputStream

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var deviceAddress = intent.getStringExtra("deviceAddress")
        var deviceName = intent.getStringExtra("deviceName")
        var isDarkMode = intent.getBooleanExtra("isDarkMode", false)

        // 创建并启动后台线程
        val backgroundThread = HandlerThread("BluetoothThread")
        backgroundThread.start()
        val backgroundHandler = Handler(backgroundThread.looper)
        val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

        // 在后台线程中建立蓝牙连接
        backgroundHandler.post {
            try {
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // 已经具有蓝牙连接权限，可以执行相关操作
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return@post
                }

                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID)
                bluetoothSocket.connect()
                outputStream = bluetoothSocket.outputStream

                runOnUiThread {
                    // 在主线程中更新UI或执行其他操作
                    // 例如，在这里可以通知ChatScreen或其他需要蓝牙连接的组件，连接已成功建立
                    // 并传递相关的蓝牙套接字或输出流
                }

            } catch (e: IOException) {
                // 处理连接异常
                Toast.makeText(this@ChatActivity, "蓝牙连接建立失败", Toast.LENGTH_SHORT).show()
                Log.d("ChatActivity", "蓝牙连接建立失败")
                e.printStackTrace()
                runOnUiThread {
                    // 在主线程中更新UI或执行其他操作
                    // 例如，在这里可以通知ChatScreen或其他需要蓝牙连接的组件，连接建立失败
                }
            }
        }

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
        fun sendMessage(message: Message)
    }

    fun sendMessage(message: Message) {
        val outputStream = bluetoothSocket?.outputStream

        try {
            val messageBytes = message.content.toByteArray()
            outputStream?.write(messageBytes)

            // 添加日志消息
            Log.d("ChatActivity", "消息发送 - 成功: $message")

        } catch (e: IOException) {
            // 发送消息失败
            Log.d("ChatActivity", "消息发送 - 失败: IOException")
            Toast.makeText(this@ChatActivity, "消息发送失败，蓝牙连接异常", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
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