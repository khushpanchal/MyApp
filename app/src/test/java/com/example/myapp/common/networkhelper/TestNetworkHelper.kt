package com.example.myapp.common.networkhelper

class TestNetworkHelper: NetworkHelper {
    override fun isNetworkConnected(): Boolean {
        return true
    }
}