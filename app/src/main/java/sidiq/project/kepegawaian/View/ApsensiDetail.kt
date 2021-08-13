package sidiq.project.kepegawaian.View

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.IDABSENSI
import sidiq.project.kepegawaian.model.absensi.absensiResponse
import sidiq.project.kepegawaian.model.absensiInsert.AbsensiInsertResponse

import java.io.File
import java.util.*

private const val REQUEST_CODE = 42
private lateinit var photoFile: File
private const val FIlE_NAME = "photo.jpg"
private var sharedPreferences: PreferenceManager? = null

class ApsensiDetail : AppCompatActivity() {

    companion object {
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }


    var tz: TimeZone? = TimeZone.getTimeZone("GMT+7")
    val c = Calendar.getInstance(tz)
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)
    val second = c.get(Calendar.MILLISECOND)

    var lat: Double = 0.0
    var jarak: Double? = 0.0
    var lokasi: String? = null


    val date = "" + year + "-" + month + "-" + day
    val waktu = "" + hour + ":" + minute


    var calender = Calendar.getInstance()
    val time = java.text.DateFormat.getDateTimeInstance().format(calender.time)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apsensi_detail)
        sharedPreferences = PreferenceManager(this)

        tvTanggal.text = "$time"


        Log.e("jumlah jam ", "" + hour)






        btnAbsensiPagi.setOnClickListener {
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
                Toast.makeText(applicationContext, "telah melakukan absensi", Toast.LENGTH_SHORT)
                    .show()
                getCurrentLocation()
                tambahAbsensi()
            }
        }

        btnAbsensiSore.setOnClickListener {
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
                Toast.makeText(
                    applicationContext,
                    "telah melakukan absensi sore ",
                    Toast.LENGTH_SHORT
                ).show()
                getCurrentLocation()
                tambahAbsensiSore()
            }
        }











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


    private fun InsertAbsensi(
        nip: Int,
        tanggal: String,
        jam_masuk: String,
        alamat: String,
        keterangan: String
    ) {
        val retrofit = ApiServices.restApi()

        val c = Calendar.getInstance()


        retrofit.InsertAbsensi(
            nip, tanggal, jam_masuk,
            alamat, keterangan, "Bearer " + sharedPreferences?.getToken()
        )
            .enqueue(object : retrofit2.Callback<AbsensiInsertResponse> {
                override fun onFailure(call: Call<AbsensiInsertResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<AbsensiInsertResponse>,
                    response: Response<AbsensiInsertResponse>
                ) {
                    val dataAbsensi = response.body()
                    if (response.isSuccessful) {

                        sharedPreferences?.saveIdAbsensi(IDABSENSI, dataAbsensi!!.data.id_absensi)
                        Log.e("data id masuk", " " + sharedPreferences?.getIdAbsensi())

                    }

                    Log.e("data abnsensi tersimpan", "")

                }

            })


    }


    private fun InsertAbsensiSore(

        jam_selesai: String,
        alamat_sore: String,
        keterangan_sore: String
    ) {
        val retrofit = ApiServices.restApi()




        retrofit.InsertAbsensiSore(
            sharedPreferences?.getIdAbsensi()!!, jam_selesai, alamat_sore, keterangan_sore
            , "Bearer " + sharedPreferences?.getToken()
        )
            .enqueue(object : retrofit2.Callback<AbsensiInsertResponse> {
                override fun onFailure(call: Call<AbsensiInsertResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<AbsensiInsertResponse>,
                    response: Response<AbsensiInsertResponse>
                ) {
                    val dataAbsensi = response.body()
                    if (response.isSuccessful) {


                    }

                    Log.e("data abnsensi tersimpan", "")

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
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(this@ApsensiDetail)
                        .removeLocationUpdates(this)
                    if (locationResult != null && locationResult.locations.size > 0) {
                        val locIndex = locationResult.locations.size - 1

                        val latitude = locationResult.locations.get(locIndex).latitude
                        val longitude = locationResult.locations.get(locIndex).longitude

                        lat = latitude


                        tvLatitude.text = "$latitude"
                        Log.e("latitude", "$latitude")
                        tvLongitude.text = "Longitude: " + longitude

                        addresses = geocoder.getFromLocation(latitude, longitude, 1)


                        var address: String = addresses[0].getAddressLine(0)
                        tvAddress.text = address

                        lokasi = address

                        jarak = getDistance(latitude, longitude, latitude, longitude)


                        if (tvAddress != null) {
                            loader.visibility = View.GONE
                        }

                    }
                }
            }, Looper.getMainLooper())

    }

    var data: String? = null
    private fun tambahAbsensi() {

        if (jarak!! <= 5.00) {


            if (hour <= 7 && minute <= 15) {


                data = "hadir"


            } else if (hour <= 7 && minute <= 30) {
                data = "terlambat"

            } else {
                data = "alfa"
            }



            Log.e("bisa ambil apsen ", "$jarak")
        } else {

            Log.e("tidak bisa ambil absen", "$jarak ")
        }

        InsertAbsensi(
            sharedPreferences?.getNip()!!,
            date,
            "$waktu",
            "$lokasi",
            data!!
        )
    }


    private fun tambahAbsensiSore() {

        if (jarak!! <= 1.00) {


            if (hour <= 7 && minute <= 15) {


                data = "hadir"


            } else if (hour <= 7 && minute <= 30) {
                data = "terlambat"

            } else {
                data = "alfa"
            }



            Log.e("bisa ambil apsen ", "$jarak")
        } else {

            Log.e("tidak bisa ambil absen", "$jarak ")
        }

        InsertAbsensiSore(

            "$waktu",
            lokasi!!,
            data!!
        )
    }


    private fun getDistance(
        latitudeSekolah: Double,
        longitudeSekolah: Double,
        latitudeUser: Double,
        longitudeUser: Double
    ): Double? {
        /* VARIABLE */
        val pi = 3.14159265358979
        val R = 6371e3
        val latRad1 = latitudeSekolah * (pi / 180)
        val latRad2 = latitudeUser * (pi / 180)
        val deltaLatRad = (latitudeUser - latitudeSekolah) * (pi / 180)
        val deltaLonRad = (longitudeUser - longitudeSekolah) * (pi / 180)

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


