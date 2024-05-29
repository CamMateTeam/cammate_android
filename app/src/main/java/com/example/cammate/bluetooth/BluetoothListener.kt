package com.example.cammate.bluetooth

import android.bluetooth.BluetoothDevice

interface BluetoothListener {
    fun deviceFound(device: BluetoothDevice?)
    fun discoveryFinished()
}