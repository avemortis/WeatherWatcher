package com.weather.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

@SuppressLint("MissingPermission")
fun Fragment.registerLocation(onSuccess: (location: Location) -> Unit, onGpsSwitchOff: () -> Unit) {
    val location =
        LocationServices.getFusedLocationProviderClient(requireActivity())

    location.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
        .addOnSuccessListener {
            if (it == null) onGpsSwitchOff()
            else onSuccess(it)
        }
}

fun Fragment.toGpsSettingsIntent() {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    startActivity(intent)
}

fun Fragment.getGpsPermissionActivityLauncher(
    onSuccess: (location: Location) -> Unit,
    onLoad: () -> Unit,
    onGpsSwitchOff: () -> Unit,
    onPermissionNotGranted: () -> Unit = {}
) = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) { permissions ->
    if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
        onLoad()
        registerLocation(onSuccess, onGpsSwitchOff)
    } else {
        onPermissionNotGranted()
    }
}

fun launchGpsPermissionAsk(
    locationPermissionRequest: ActivityResultLauncher<Array<String>>
) {
    locationPermissionRequest.launch(
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
}