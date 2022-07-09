package edu.seminolestate.getuserlocation

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    lateinit var txt: TextView
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var radioGroup: RadioGroup

    val mountEverestLat = 27.9881
    val mountEverestLong = 86.9250
    val tajMahalLat = 27.1751
    val tajMahalLong = 78.0421
    val eiffelTowerLat = 48.8584
    val eiffelTowerLong = 2.2945

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        txt = findViewById(R.id.distance_textVew)
        radioGroup = findViewById(R.id.radioGroup)

        findViewById<Button>(R.id.btn_calculate_distance).setOnClickListener{
            fetchLocation()
        }

    }

    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation
        var radioGroupID = radioGroup.checkedRadioButtonId

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if (it != null && radioGroupID == R.id.rb_option_Mount_Everest){

                val mountEverestDistance = haversine(it.latitude, it.longitude, mountEverestLat, mountEverestLong) / 1.609344
                txt.setText(String.format("You are %.2f miles away from Mount Everest", mountEverestDistance))
                //txt.setText("${it.latitude} ${it.longitude}")
                //Toast.makeText(applicationContext, "${mountEverestDistance}", Toast.LENGTH_SHORT).show()
            }
            if (it != null && radioGroupID == R.id.rb_option_Taj_Mahal){

                val tajMahalDistance = haversine(it.latitude, it.longitude, tajMahalLat, tajMahalLong) / 1.609344
                txt.setText(String.format("You are %.2f miles away from the Taj Mahal", tajMahalDistance))
                //txt.setText("${it.latitude} ${it.longitude}")
                //Toast.makeText(applicationContext, "${mountEverestDistance}", Toast.LENGTH_SHORT).show()
            }
            if (it != null && radioGroupID == R.id.rb_option_Eiffel_Tower){

                val eiffelTowerDistance = haversine(it.latitude, it.longitude, eiffelTowerLat, eiffelTowerLong) / 1.609344
                txt.setText(String.format("You are %.2f miles away from the Eiffel Tower", eiffelTowerDistance))
                //txt.setText("${it.latitude} ${it.longitude}")
                //Toast.makeText(applicationContext, "${mountEverestDistance}", Toast.LENGTH_SHORT).show()
            }


        }
    }

    //haversine formula
    fun haversine(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        // distance between latitudes and longitudes
        var lat1 = lat1
        var lat2 = lat2
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        // convert to radians
        lat1 = Math.toRadians(lat1)
        lat2 = Math.toRadians(lat2)

        // apply formulae
        val a = Math.pow(Math.sin(dLat / 2), 2.0) +
                Math.pow(Math.sin(dLon / 2), 2.0) *
                Math.cos(lat1) *
                Math.cos(lat2)
        val rad = 6371.0
        val c = 2 * Math.asin(Math.sqrt(a))
        return rad * c
    }

}