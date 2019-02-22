package schellekens.mqttgeofence


import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import schellekens.mqttgeofence.fragments.BrokerFragment
import schellekens.mqttgeofence.fragments.FenceFragment


class MainActivity : MqttActivity() {

    override fun onMqttConnected() {
        shortToast("MQTT Connected")
    }

    override fun onMqttDisconnected() {
        shortToast("MQTT Disconnected")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            val brokerFragment = getBrokerFragment(DEFAULT_HOST, DEFAULT_CLIENT, DEFAULT_PORT)
            val fenceFragment = getFenceFragment(null, DEFAULT_RADIUS, DEFAULT_LOCATION_SERVICE)

            supportFragmentManager.inTransaction{
                add(flTop.id, brokerFragment)
            }

            supportFragmentManager.inTransaction {
                add(flBottom.id, fenceFragment)
            }
        }
    }

    fun getFenceFragment(location: Location?, radius: Double, locationService: String) : FenceFragment{
        val bundle = Bundle()
        bundle.putParcelable(FenceFragment.ARG_LOCATION, location)
        bundle.putDouble(FenceFragment.ARG_RADIUS, radius)
        bundle.putString(FenceFragment.ARG_LOCATION_SERVICE, locationService)

        val fragment = FenceFragment()
        fragment.arguments = bundle

        return fragment
    }

    fun getBrokerFragment(host: String, client: String, port: Int): BrokerFragment {
        val bundle = Bundle()
        bundle.putString(BrokerFragment.ARG_HOST, host)
        bundle.putString(BrokerFragment.ARG_CLIENT_ID, client)
        bundle.putInt(BrokerFragment.ARG_PORT, port)

        val fragment = BrokerFragment()
        fragment.arguments = bundle
        return fragment
    }

    companion object {
        const val TAG = "MainActivity"
        const val DEFAULT_HOST = "192.168.1.11"
        const val DEFAULT_PORT = 1883
        const val DEFAULT_CLIENT = "AndroidApp"
        const val DEFAULT_RADIUS = 15.0
        const val DEFAULT_LOCATION_SERVICE = Context.LOCATION_SERVICE
    }
}
