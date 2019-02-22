package schellekens.mqttgeofence.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import schellekens.mqttgeofence.R
import kotlinx.android.synthetic.main.fragment_broker.*

interface OnBrokerFragmentChangedListener {
    fun onConnect(host: String, identifier: String, port: Int)
}

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BrokerFragment : Fragment() {

    private var mHost: String? = null
    private var mClientId: String? = null
    private var mPort: Int = 1883
    private var mListener: OnBrokerFragmentChangedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString(ARG_HOST)?.let{
            mHost = it
        }

        arguments?.getString(ARG_CLIENT_ID).let{
            mClientId = it
        }

        arguments?.getInt(ARG_PORT).let{
            mPort = it!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_broker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!mHost.isNullOrBlank()){
            etBrokerUri.setText(mHost)
        }

        if(!mClientId.isNullOrBlank()){
            etClientId.setText(mClientId)
        }

        etPort.setText(mPort)

        btnConnect.setOnClickListener{
            mListener?.onConnect(etBrokerUri.text.toString(), etClientId.text.toString(), etPort.text.toString().toInt())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBrokerFragmentChangedListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnBrokerFragmentChangedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {
        const val TAG = "BrokerFragment"

        const val ARG_HOST = "Host"
        const val ARG_CLIENT_ID = "ClientId"
        const val ARG_PORT = "Port"

        @JvmStatic
        fun newInstance(host: String?, clientId: String?, port: Int?) =
            BrokerFragment().apply {
                arguments = Bundle().apply {
                    if(!host.isNullOrBlank()){
                        putString(ARG_HOST, host)
                    }

                    if(!clientId.isNullOrBlank()){
                        putString(ARG_CLIENT_ID, clientId)
                    }

                    if(port != null){
                        putInt(ARG_PORT, port)
                    }
                }
            }
    }
}
