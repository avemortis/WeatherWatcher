package com.weather.ui.fragment.gpspermission

import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.fragment.findNavController
import com.weather.R
import com.weather.data.network.WeatherService
import com.weather.databinding.FragmentGpsPermissionBinding
import com.weather.utils.WeatherFragmentFactory
import com.weather.utils.getGpsPermissionActivityLauncher
import com.weather.utils.launchGpsPermissionAsk
import com.weather.utils.toGpsSettingsIntent

class GpsPermissionFragment : Fragment() {
    private lateinit var viewModel: GpsPermissionViewModel
    private lateinit var bind: FragmentGpsPermissionBinding
    private lateinit var gpsActivityListener: ActivityResultLauncher<Array<String>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentGpsPermissionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[GpsPermissionViewModel::class.java]
        getLocation()
        return bind.root
    }

    private fun getLocation() {
        gpsActivityListener =
            getGpsPermissionActivityLauncher({ setLocationToShowState(it) },
                { setLoadingState()},
                { setEnableGpsState() },
                { setAskPermissionAgainState() })
        launchGpsPermissionAsk(gpsActivityListener)
    }

    private fun setLoadingState() {
        hideButtons()
        bind.gpsPermissionText.visibility = View.INVISIBLE
        bind.gpsPermissionProgress.visibility = View.VISIBLE
    }

    private fun setLocationToShowState(location: Location) {
        val service = WeatherService()
        parentFragmentManager.fragmentFactory = WeatherFragmentFactory(service, location)
        findNavController().navigate(GpsPermissionFragmentDirections.actionGpsPermissionFragmentToWeatherWatchFragment())
        hideButtons()
        bind.gpsPermissionProgress.visibility = View.INVISIBLE
        setText(location.toString())
    }

    private fun setEnableGpsState() {
        bind.gpsPermissionProgress.visibility = View.INVISIBLE
        setText(getString(R.string.gps_off))
        bind.gpsPermissionButtomButton.visibility = View.VISIBLE
        bind.gpsPermissionButtomButton.text = getString(R.string.turn_on_gps)
        bind.gpsPermissionButtomButton.setOnClickListener {
            toGpsSettingsIntent()
        }
        bind.gpsPermissionRetry.visibility = View.VISIBLE
        bind.gpsPermissionRetry.setOnClickListener {
            launchGpsPermissionAsk(gpsActivityListener)
        }
    }

    private fun setAskPermissionAgainState() {
        bind.gpsPermissionProgress.visibility = View.INVISIBLE
        setText(getString(R.string.gps_permission_not_granted))
        bind.gpsPermissionButtomButton.visibility = View.VISIBLE
        bind.gpsPermissionButtomButton.text = getString(R.string.give_permission)
        bind.gpsPermissionButtomButton.setOnClickListener {
            launchGpsPermissionAsk(gpsActivityListener)
        }
    }

    private fun setText(text: String) {
        bind.gpsPermissionText.visibility = View.VISIBLE
        bind.gpsPermissionText.text = text
    }

    private fun hideButtons() {
        bind.gpsPermissionRetry.visibility = View.INVISIBLE
        bind.gpsPermissionButtomButton.visibility = View.INVISIBLE
    }
}