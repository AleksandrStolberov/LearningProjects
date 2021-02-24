package com.skillbox.permissionsanddate

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.ConnectionResult.*
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_location.*
import kotlinx.android.synthetic.main.item_location.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class LocationFragment : Fragment(R.layout.fragment_location) {

    private var locationList: ArrayList<Location> = arrayListOf()

    private var locationAdapter: LocationAdapter? = null

    private val pictureList = listOf(
        "http://i.youfon.net/medium/2018-12/1543706885.jpg",
        "http://i.youfon.net/medium/2017-03/1489336892.jpg",
        "http://i1.youfon.net/medium/2017-08/1503827944.jpg",
        "http://i2.youfon.net/medium/2020-03/1584047219.jpg",
        "http://i.youfon.net/medium/2017-11/1510689020.jpg"
    )

    private var location = "Запрос откланен"

    private var selectedDateTime: Instant? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest

    private var dialogDate: DatePickerDialog? = null
    private var dialogTime: TimePickerDialog? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            locationList =
                savedInstanceState.getParcelableArrayList<Location>(KEY_LIST_LOCATION) ?: error("")
            location = savedInstanceState.getString(KEY_SERVICES_LOCATION) ?: error("")
            if (locationList.isEmpty()) {
                noLocationTextView.visibility = View.VISIBLE
            } else noLocationTextView.visibility = View.GONE
        }
        retainInstance = true
        initLocationList()
        getLocation()


    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        val playServices =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(requireContext())
        if (playServices == ConnectionResult.SUCCESS) {
            val isPermission = isPermissionLocation()
            if (isPermission) {
                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                fusedLocationProviderClient.lastLocation
                        .addOnSuccessListener {
                            it?.let {
                                location = """
                        Lat = ${it.latitude}
                        Lng =  ${it.longitude}
                        Alt = ${it.altitude}
                        Speed = ${it.speed}
                    """.trimIndent()
                            } ?: location
                        }
                        .addOnCanceledListener {
                            location = "Локация отстутсвует"
                        }
                        .addOnFailureListener {
                            location = "Запрос локации завершился неудачно"
                        }
                createLocationItem()
                view?.run {
                    updateLocation()
                }
            } else {
                requireActivity().supportFragmentManager.popBackStack()
            }
        } else {
            val playServicesDialog = GoogleApiAvailability.getInstance()
            when(playServices) {
                SERVICE_MISSING -> playServicesDialog.getErrorDialog(requireActivity(), SERVICE_MISSING, playServices)
                SERVICE_VERSION_UPDATE_REQUIRED -> playServicesDialog.getErrorDialog(requireActivity(), SERVICE_VERSION_UPDATE_REQUIRED, playServices)
                SERVICE_DISABLED -> playServicesDialog.getErrorDialog(requireActivity(), SERVICE_DISABLED, playServices)
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun updateLocation() {
        locationRequest = LocationRequest().apply {
            interval = TimeUnit.SECONDS.toMillis(60)
            fastestInterval = TimeUnit.SECONDS.toMillis(30)
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return
                    val updateLocation = """
                        Lat = ${locationResult.lastLocation.latitude}
                        Lng =  ${locationResult.lastLocation.longitude}
                        Alt = ${locationResult.lastLocation.altitude}
                        Speed = ${locationResult.lastLocation.speed}
                    """.trimIndent()
                    if (updateLocation != location) {
                        val newLocation = Location(
                            id = Random.nextLong(),
                            createdAt = selectedDateTime ?: Instant.now(),
                            location = updateLocation,
                            picture = pictureList.random()
                        )

                        locationList =
                            (arrayListOf(newLocation) + locationList) as ArrayList<Location>
                        locationAdapter?.submitList(locationList)
                        locationRecyclerView.postDelayed({
                            locationRecyclerView.scrollToPosition(0)
                        }, 50)
                    }
                }
            },
            Looper.myLooper()
        )
    }

    private fun initLocationList() {
        with(locationRecyclerView) {
            locationAdapter = LocationAdapter { initTimiPicker() }
            adapter = locationAdapter
            val linearLayout = LinearLayoutManager(requireContext())
            layoutManager = linearLayout
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun createLocationItem() {
        getLocationButton.setOnClickListener {
            val newLocation = Location(
                id = Random.nextLong(),
                createdAt = selectedDateTime ?: Instant.now(),
                location = location,
                picture = pictureList.random()
            )

            locationList = (arrayListOf(newLocation) + locationList) as ArrayList<Location>
            locationAdapter?.submitList(locationList)
            locationRecyclerView.postDelayed({
                locationRecyclerView.scrollToPosition(0)
            }, 50)
            if (locationList.isEmpty()) {
                noLocationTextView.visibility = View.VISIBLE
            } else noLocationTextView.visibility = View.GONE
        }

    }

    private fun initTimiPicker() {
        val currentDateTime = LocalDateTime.now()
        dialogDate = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                dialogTime = TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        val zonedDateTime =
                            LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
                                    .atZone(ZoneId.systemDefault())
                        selectedDateTime = zonedDateTime.toInstant()
                        val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
                                .withZone(ZoneId.systemDefault())
                        dateTextView.text = formatter.format(selectedDateTime)
                    },
                    currentDateTime.hour,
                    currentDateTime.minute,
                    true
                ).apply {
                    show()
                }
            },
            currentDateTime.year,
            currentDateTime.month.value - 1,
            currentDateTime.dayOfMonth
        ).apply {
            show()
        }
    }

    private fun isPermissionLocation(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_LIST_LOCATION, locationList)
        outState.putString(KEY_SERVICES_LOCATION, location)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        locationAdapter = null
        dialogDate?.dismiss()
        dialogDate = null
        dialogTime?.dismiss()
        dialogTime = null
        selectedDateTime = null
    }

    companion object {
        private const val KEY_LIST_LOCATION = "KEY_LIST_LOCATION"
        private const val KEY_SERVICES_LOCATION = "KEY_SERVICES_LOCATION"
    }

}