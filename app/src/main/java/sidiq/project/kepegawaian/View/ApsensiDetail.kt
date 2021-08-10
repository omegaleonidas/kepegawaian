package sidiq.project.kepegawaian.View

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_apsensi_detail.*
import retrofit2.Call
import retrofit2.Response
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.model.absensi.absensiResponse
import java.io.File
import java.util.*
import kotlin.math.log

private const val REQUEST_CODE = 42
private lateinit var photoFile: File
private const val FIlE_NAME = "photo.jpg"
private var sharedPreferences: PreferenceManager? = null

class ApsensiDetail : AppCompatActivity() {

    companion object {
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val date = "" + year + "-" + month + "-" + day


    var calender = Calendar.getInstance()
    val time = java.text.DateFormat.getDateTimeInstance().format(calender.time)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apsensi_detail)
        sharedPreferences = PreferenceManager(this)

        tvTanggal.text = "$time"












        btnGetLocation.setOnClickListener {
            //check permission
            if (ContextCompat.checkSelfPermission(
                    applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@ApsensiDetail,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    , REQUEST_PERMISSION_REQUEST_CODE
                )
            } else {
                tvAddress.text = ""
                tvLatitude.text = ""
                tvLongitude.text = ""
                loader.visibility = View.VISIBLE
                getCurrentLocation()

            }
        }


    }







    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //  val takenImage = data?.extras?.get("data") as Bitmap
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            imageView.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }


    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_REQUEST_CODE && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(this@ApsensiDetail, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //nip:Int,tanggal:String,jam_masuk:String,alamat:String,keterangan:String

    private fun InsertAbsensi(){
        val retrofit = ApiServices.restApi()

        val c = Calendar.getInstance()


        retrofit.InsertAbsensi(sharedPreferences?.getNip()!!,date,time,
            "secata b","hadir","Bearer " + sharedPreferences?.getToken())
            .enqueue(object :retrofit2.Callback<absensiResponse>{
                override fun onFailure(call: Call<absensiResponse>, t: Throwable) {
              Log.e("res tidak masuk ",t.message)
                }

                override fun onResponse(
                    call: Call<absensiResponse>,
                    response: Response<absensiResponse>
                ) {
                   Log.e("data abnsensi tersimpan","")
                }

            })







    }


    private fun getCurrentLocation() {

        var locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //now getting address from latitude and longitude

        val geocoder = Geocoder(this@ApsensiDetail, Locale.getDefault())
        var addresses: List<Address>

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.getFusedLocationProviderClient(this@ApsensiDetail)
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(this@ApsensiDetail)
                        .removeLocationUpdates(this)
                    if (locationResult != null && locationResult.locations.size > 0) {
                        var locIndex = locationResult.locations.size - 1

                        var latitude = locationResult.locations.get(locIndex).latitude
                        var longitude = locationResult.locations.get(locIndex).longitude


                        tvLatitude.text = "$latitude"
                        Log.e("latitude","$latitude")
                        tvLongitude.text = "Longitude: " + longitude

                        addresses = geocoder.getFromLocation(latitude, longitude, 1)


                        var address: String = addresses[0].getAddressLine(0)
                        tvAddress.text = address

                        var jarak = getDistance(latitude,longitude,latitude,longitude)

                        if (jarak!! <=50.00){
                            InsertAbsensi()
                            Log.e("bisa ambil apsen ","$jarak")
                        }else{

                            Log.e("tidak bisa ambil absen","$jarak ")
                        }

                        if (tvAddress != null) {
                            loader.visibility = View.GONE
                        }

                    }
                }
            }, Looper.getMainLooper())

    }






    private fun getDistance(
        latitudeTujuan: Double,
        longitudeTujuan: Double,
        latitudeUser: Double,
        longitudeUser: Double
    ): Double? {
        /* VARIABLE */
        val pi = 3.14159265358979
        val R = 6371e3
        val latRad1 = latitudeTujuan * (pi / 180)
        val latRad2 = latitudeUser * (pi / 180)
        val deltaLatRad = (latitudeUser - latitudeTujuan) * (pi / 180)
        val deltaLonRad = (longitudeUser - longitudeTujuan) * (pi / 180)

        /* RUMUS HAVERSINE */
        val a =
            Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) + Math.cos(
                latRad1
            ) * Math.cos(latRad2) * Math.sin(deltaLonRad / 2) * Math.sin(
                deltaLonRad / 2
            )
        val c =
            2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return R * c
    }





}