package com.example.hamrofutsal

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await



@Composable
@SuppressLint("CoroutineCreationDuringComposition")
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
fun CurrentLocationContent(usePreciseLocation: Boolean,mapUrl: String) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var longitude: Double = 85.3272174 // Initialize with default value
    var latitude: Double = 27.658694 // Initialize with default value

    scope.launch(Dispatchers.IO) {
        val priority = if (usePreciseLocation) {
            Priority.PRIORITY_HIGH_ACCURACY
        } else {
            Priority.PRIORITY_BALANCED_POWER_ACCURACY
        }
        val result = locationClient.getCurrentLocation(
            priority,
            CancellationTokenSource().token,
        ).await()
        result?.let { fetchedLocation ->
            // Update longitude and latitude when location data is available
            longitude = fetchedLocation.longitude
            latitude = fetchedLocation.latitude

            val intent = Intent(
                Intent.ACTION_VIEW,
                //Uri.parse("https://www.google.com/maps/@${latitude},${longitude},14.77z?entry=ttu")
                Uri.parse("https://www.google.com/maps/dir/${latitude},${longitude}${mapUrl}")
            )
            context.startActivity(intent)
        }


    }
}