package sample.com.matches.util

import com.google.gson.stream.MalformedJsonException
import java.net.ConnectException
import java.net.SocketTimeoutException

class ErrorParser {

    fun getErrorMessage(trowable:Throwable):String{
        when (trowable){
            is SocketTimeoutException -> return "Request Timeout. Please try again"
            is MalformedJsonException -> return "Parsing error. Please try again"
            is ConnectException -> return "Connection Error. Please try again"
            else -> return "Connection Error. Please try again"
        }
    }
}