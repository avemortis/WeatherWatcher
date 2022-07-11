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
import com.weather.databinding.FragmentGpsPermissionBinding
import com.weather.di.DaggerWeatherComponent
import com.weather.di.WeatherServiceModule
import com.weather.utils.WeatherFragmentFactory
import com.weather.utils.getGpsPermissionActivityLauncher
import com.weather.utils.launchGpsPermissionAsk
import com.weather.utils.toGpsSettingsIntent

class GpsPermissionFragment : Fragment() {
    private val builder = DaggerWeatherComponent.builder()
    private lateinit var bind: FragmentGpsPermissionBinding
    private val gpsActivityListener =
        getGpsPermissionActivityLauncher({ goToWeatherWatchingFragment(it) },
            { setLoadingState()},
            { setEnableGpsState() },
            { setAskPermissionAgainState() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentGpsPermissionBinding.inflate(inflater, container, false)
        getLocation()
        return bind.root
    }

    private fun getLocation() {
        launchGpsPermissionAsk(gpsActivityListener)
    }

    private fun setLoadingState() {
        hideButtons()
        bind.gpsPermissionText.visibility = View.INVISIBLE
        bind.gpsPermissionProgress.visibility = View.VISIBLE
    }

    private fun goToWeatherWatchingFragment(location: Location) {
        val component = builder.weatherServiceModule(WeatherServiceModule(location)).build()
        requireActivity().supportFragmentManager.fragmentFactory = component.getFragmentFactory()
        findNavController().navigate(GpsPermissionFragmentDirections.actionGpsPermissionFragmentToWeatherWatchFragment())
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