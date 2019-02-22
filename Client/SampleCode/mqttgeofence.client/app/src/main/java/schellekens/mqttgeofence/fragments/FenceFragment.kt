package schellekens.mqttgeofence.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_fence.*

import schellekens.mqttgeofence.R

interface OnFenceFragmentChangedListener{
    fun onConnect(loc: Location, rad: Double, locService: String)
}

class FenceFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mLocation: Location? = null
    private var mRadius: Double? = null
    private var mLocationService: String? = null

    private var mListener: OnFenceFragmentChangedListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getDouble(ARG_RADIUS)?.let{
            mRadius = it
        }

        arguments?.getParcelable<Location>(ARG_LOCATION)?.let{
            mLocation = it
        }

        arguments?.getString(ARG_LOCATION_SERVICE)?.let {
            mLocationService = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(mLocationService == null){
            throw IllegalArgumentException("LocationService cannot be null")
        }

        if(mLocation != null){
            tvLatitude.text = mLocation?.latitude.toString()
           tvLongitude.text = (mLocation?.longitude.toString())
        }

        if(mRadius != null){
            etRadius.tag = mRadius!!.toString()
        } else {
            etRadius.tag = "20.0"
        }

        btnSetLocation.setOnClickListener {
            val locationManager = activity!!.getSystemService(mLocationService!!) as LocationManager
            val locationProvider: String = LocationManager.GPS_PROVIDER

            val permission = ContextCompat.checkSelfPermission(context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Permission to Location is denied")
            } else {
                val lastKnownLocation: Location = locationManager.getLastKnownLocation(locationProvider)
                tvLatitude.text = lastKnownLocation.latitude.toString()
                tvLongitude.text = lastKnownLocation.longitude.toString()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFenceFragmentChangedListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {

        const val TAG = "FenceFragment"

        const val ARG_LOCATION = "Location"
        const val ARG_RADIUS = "Radius"
        const val ARG_LOCATION_SERVICE = "LocationService"

        const val PLACE_PICKER_REQUEST_CODE = 1


        @JvmStatic
        fun newInstance(loc: Location?, rad: Double?, locService: String?) =
            FenceFragment().apply {
                arguments = Bundle().apply {
                    if(loc != null) {
                        putParcelable(ARG_LOCATION, loc)
                    }

                    if(rad != null){
                        putDouble(ARG_RADIUS, rad)
                    }

                    if(locService != null){
                        putString(ARG_LOCATION_SERVICE, locService)
                    }
                }
            }


    }
}
