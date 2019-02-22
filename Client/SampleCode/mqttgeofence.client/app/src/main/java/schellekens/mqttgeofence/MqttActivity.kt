package schellekens.mqttgeofence

import android.content.*
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.service.carrier.CarrierIdentifier
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import se.wetcat.qatja.MQTTConstants
import se.wetcat.qatja.android.MQTTConnectionConstants
import se.wetcat.qatja.android.QatjaService
import se.wetcat.qatja.messages.MQTTPublish
import java.lang.Exception

interface InternetListener {
    fun onLoosingInternet()
    fun onGainedInternet()
}

abstract class MqttActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MqttActivity"
    }

    lateinit var mClient: QatjaService
    private var isBound = false
    private var isBinding = false

    private val mHandler = Handler(MqttCallback())

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mClient = (service as QatjaService.QatjaBinder).service as QatjaService

            isBound = true
            isBinding = false
            mClient.setHandler(mHandler)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            isBinding = false
        }
    }

    private val mConnectivityManager: ConnectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private var mCurrentInternetState = false
      private set(value){
          if(value != field){
              field = value
              if(field) {
                  onGainedInternet()
              } else {
                  onLoosingInternet()
              }
          }
      }

    private val mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            StringBuilder().apply {
                append("Action: ${intent?.action}\n")
                append("URI: ${intent?.toUri(Intent.URI_INTENT_SCHEME)}\n")
                toString().also { log ->
                    Log.d(TAG, log)
                    Toast.makeText(context, log, Toast.LENGTH_LONG).show()
                }

                val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

                mCurrentInternetState = activeNetwork?.isConnected == true
            }
        }
    }

    protected fun attemptSubscribeToTopic(topic: String){
        Log.i(TAG, "attemptSubscribeToTopic($topic)")

        launch(UI){
            try{
                mClient.subscribe(topic, MQTTConstants.AT_MOST_ONCE)
            } catch (ex: Exception){
                Log.e(TAG, ex.message, ex)
            }
        }
    }

    override fun onResume(){
        super.onResume()

        registerReceiver(mBroadcastReceiver, IntentFilter().apply {
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        })

        attemptBindService()
    }

    override fun onPause() {
        try{
            mClient.disconnect()
            unbindService(mConnection)

            isBinding = false
            isBound = false
        } catch (ex: Exception){
            Log.e(TAG, ex.message, ex)
        }

        unregisterReceiver(mBroadcastReceiver)
        super.onPause()
    }

    abstract fun onMqttConnected()

    abstract fun onMqttDisconnected()

    private fun handleStateChange(msg: Message){
        Log.d(TAG, "{ method: 'handleStateChange($msg)', msg.arg1: '${msg.arg1}' }")
        Log.i(TAG, MQTTConnectionConstants.resolveStateName(msg.arg1))

        when(msg.arg1){
            MQTTConnectionConstants.STATE_NONE  -> {
                onMqttDisconnected()
            }
            MQTTConnectionConstants.STATE_CONNECTING -> {
            }
            MQTTConnectionConstants.STATE_CONNECTED -> {
                onMqttConnected()
            }
            MQTTConnectionConstants.STATE_CONNECTION_FAILED -> {
                onMqttDisconnected()
            }
            else -> {
                Log.e(TAG, "Unhandled MQTT state change")
            }
        }
    }

    private fun onLoosingInternet() {
        mClient.disconnect()
    }

    private fun onGainedInternet() {
        if(isBound){
            // Do Nothing
        } else if (!isBound && !isBinding){
            attemptBindService()
        }
    }

    private fun attemptBindService() {
        Log.d(TAG, "{ method: 'attemptBindService()', isBound: '$isBound', isBinding: '$isBinding' }")

        if(!isBound && !isBinding) {
            isBinding = true

            Intent(this@MqttActivity, QatjaService::class.java).apply{
                bindService(this, mConnection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    private fun attemptConnectMqtt(host: String, identifier: String, port: Int){
        Log.d(TAG, "attemptConnectMqtt($host, $identifier)")

        if(!arrayOf(MQTTConnectionConstants.STATE_CONNECTING,
                    MQTTConnectionConstants.STATE_CONNECTED).contains(mClient.state)){
            try{
                mClient.setHost(host)
                mClient.setPort(port)
                mClient.setIdentifier(identifier)
                mClient.setKeepAlive(5000)
                mClient.setCleanSession(true)
                mClient.connect()
            } catch ( ex: Exception){
                Log.e(TAG, ex.message, ex)
            }
        }
    }

    private fun handleReceivedMessage(msg: Message){
        val publish: MQTTPublish = msg.obj as MQTTPublish
    }

    private inner class MqttCallback() : Handler.Callback {
        override fun handleMessage(msg: Message?) : Boolean {
            msg?.let{
                when (it.what){
                    MQTTConnectionConstants.STATE_CHANGE -> {
                        handleStateChange(it)
                    }
                    3 -> {
                        handleReceivedMessage(it)
                    }
                }
            }

            return true
        }
    }
}