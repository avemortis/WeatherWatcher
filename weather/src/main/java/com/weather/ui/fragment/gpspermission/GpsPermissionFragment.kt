package com.weather.ui.fragment.gpspermission

import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import com.weather.databinding.FragmentGpsPermissionBinding
import com.weather.utils.getGpsPermissionActivityLauncher
import com.weather.utils.launchPermissionAsk
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
        launchPermissionAsk(gpsActivityListener)
    }

    private fun setLoadingState() {
        hideButtons()
        bind.gpsPermissionText.visibility = View.INVISIBLE
        bind.gpsPermissionProgress.visibility = View.VISIBLE
    }

    private fun setLocationToShowState(location: Location) {
        hideButtons()
        bind.gpsPermissionRetry.visibility = View.INVISIBLE
        setText(location.toString())
    }

    private fun setEnableGpsState() {
        bind.gpsPermissionProgress.visibility = View.INVISIBLE
        setText("GPS is switch off right now. Please turn it on to watch weather")
        bind.gpsPermissionButtomButton.visibility = View.VISIBLE
        bind.gpsPermissionButtomButton.text = "Turn GPS on"
        bind.gpsPermissionButtomButton.setOnClickListener {
            toGpsSettingsIntent()
        }
        bind.gpsPermissionRetry.visibility = View.VISIBLE
        bind.gpsPermissionRetry.setOnClickListener {
            launchPermissionAsk(gpsActivityListener)
        }
    }

    private fun setAskPermissionAgainState() {
        bind.gpsPermissionProgress.visibility = View.INVISIBLE
        setText("GPS permission was not granted. Application can't show weather without location information. Please, give permission to watch weather")
        bind.gpsPermissionButtomButton.visibility = View.VISIBLE
        bind.gpsPermissionButtomButton.text = "Give permission"
        bind.gpsPermissionButtomButton.setOnClickListener {
            launchPermissionAsk(gpsActivityListener)
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