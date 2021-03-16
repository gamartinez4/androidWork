package com.example.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class MainActivity : AppCompatActivity() {
    var mybtConnectedThread:ConnectedThread? = null
    var mmSocket: BluetoothSocket?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
//        if (bluetoothAdapter == null) {
//            Log.e("Lista",bluetoothAdapter.bondedDevices.toString())
//            // Device doesn't support Bluetooth
//        }
//        else Log.e("Lista",bluetoothAdapter.bondedDevices.toString())

        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 1)
        }

        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        pairedDevices?.forEach {
            Log.e( "Dispositivos:",it.name +  " " + it.address + " " + it.uuids.toString())
            if(it.name == "KHS-633"){
                Log.e( "entro","1")
                try {
                    mmSocket=it.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"))
                    mybtConnectedThread= ConnectedThread(mmSocket!!)
                    if(mybtConnectedThread!=null) {
                        Log.e( "creo socket","bien")
                        mybtConnectedThread?.write("asdasdasda".toByteArray())
                        Log.e("escribio", "bien")
                        mybtConnectedThread?.start()
                    }
                }catch (e:Exception){
                    Log.e( "SOCKET",e.toString())
                }

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
           mybtConnectedThread?.cancel()
            Log.e( "SOCKET","socket satisfactoriamente caneclado")
        }catch (e:Exception){
            Log.e( "SOCKET","Fallo cancelacion")
        }

    }



    class ConnectedThread(private val mmSocket: BluetoothSocket) : Thread() {

            private val mmInStream: InputStream = mmSocket.inputStream
            private val mmOutStream: OutputStream = mmSocket.outputStream
            private val mmBuffer: ByteArray = ByteArray(1024) // mmBuffer store for the stream

            override fun run() {
                var numBytes: Int // bytes returned from read()
                var i = 0
                // Keep listening to the InputStream until an exception occurs.
                while (true) {
                    i++
                    // Read from the InputStream.
                   try {
                       numBytes =  mmInStream.read(mmBuffer)
                    } catch (e: Exception) {
                        Log.e("TAG", "Input stream was disconnected $i", e)
                        break
                    }
                }
            }

            // Call this from the main activity to send data to the remote device.
            fun write(bytes: ByteArray) {
                try {
                    mmOutStream.write(bytes)
                } catch (e: IOException) {
                    Log.e("TAG", "Error occurred when sending data", e)
                    return
                }
            }

            // Call this method from the main activity to shut down the connection.
            fun cancel() {
                try {
                    mmSocket.close()
                } catch (e: IOException) {
                    Log.e("TAG", "Could not close the connect socket", e)
                }
            }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//
//
//    // Register for broadcasts when a device is discovered.
//    val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
//    registerReceiver(receiver, filter)
//}
//
//    // Create a BroadcastReceiver for ACTION_FOUND.
//    private val receiver = object : BroadcastReceiver() {
//
//        override fun onReceive(context: Context, intent: Intent) {
//            val action: String = intent.action?:""
//            when(action) {
//                BluetoothDevice.ACTION_FOUND -> {
//                    // Discovery has found a device. Get the BluetoothDevice
//                    // object and its info from the Intent.
//                    val device: BluetoothDevice =
//                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
//                    val deviceName = device.name
//                    val deviceHardwareAddress = device.address // MAC address
//                }
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//
//        // Don't forget to unregister the ACTION_FOUND receiver.
//        unregisterReceiver(receiver)
//    }
}