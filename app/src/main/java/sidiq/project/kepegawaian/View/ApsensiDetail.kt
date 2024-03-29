package sidiq.project.kepegawaian.View

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.cazaea.sweetalert.SweetAlertDialog
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_apsensi_detail.*
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Response
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.IDABSENSI
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.JAM
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.TANGGAL
import sidiq.project.kepegawaian.model.absensi.absensiResponse
import sidiq.project.kepegawaian.model.absensiInsert.AbsensiInsertResponse
import sidiq.project.kepegawaian.model.jam.timeResponse
import sidiq.project.kepegawaian.model.pegawai.PegawaiRespon
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


class ApsensiDetail : AppCompatActivity() {

    companion object {
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }


    var tz: TimeZone? = TimeZone.getTimeZone("GMT+7")

    private var sharedPreferences: PreferenceManager? = null

    var waktu: String? = null
    val c = Calendar.getInstance(tz)

    val df = DecimalFormat("####.##")

    var hour: Int = 0
    var minute: Int = 0

    lateinit var alertDialog: SweetAlertDialog

    var lat: Double = 0.0
    var jarakWaktu: Double? =0.0

    var data: String? = null
    var jarak: Double? = 0.0
    var jarakUkur: String? = null


    var lokasi: String? = null


    var date: String? = null
//    val date =
//        StringBuilder().append(year).append("-").append(month).append("-").append(day).toString()
//     //   val waktu = "" + hour + ":" + minute
//

    var calender = Calendar.getInstance()
    val time = java.text.DateFormat.getDateTimeInstance().format(calender.time)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apsensi_detail)
        sharedPreferences = PreferenceManager(this)
        getCurrentLocation()
        getTime()

        alertDialog = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Bagus")
            .setContentText("anda sudah mengambil absensi")

        if (ContextCompat.checkSelfPermission(
                applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@ApsensiDetail,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                , REQUEST_PERMISSION_REQUEST_CODE
            )
        }








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
                Log.e("bottom", "data absensi pagi telah di klik ")
                getCurrentLocation()
                Log.e("tanggal hari ini", "${sharedPreferences?.getTanggal()} " )

                if(sharedPreferences?.getTanggal() == null){
                    GetData()

            }else if (sharedPreferences?.getTanggal() == date) {
                    SweetAlertDialog(this@ApsensiDetail, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("  Anda sudah ambil absen coba besok lagi  ")
                        .setConfirmText("OK")
                        .show()
                }else{
                    GetData()


                }


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

                getCurrentLocation()

                if(sharedPreferences?.getTanggal() == null){
                    SweetAlertDialog(this@ApsensiDetail, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Ambil absen Pagi terlebih dahulu ")
                        .setConfirmText("OK")
                        .show()

                }else if (sharedPreferences?.getJam() == null && sharedPreferences?.getTanggal() == date) {

                    tambahAbsensiSore()
                      }
                else if ( sharedPreferences?.getJam() != null && sharedPreferences?.getTanggal() == date){
                    SweetAlertDialog(this@ApsensiDetail, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("  Anda sudah ambil absen coba besok lagi  ")
                        .setConfirmText("OK")
                        .show()


                }else if( sharedPreferences?.getJam() != null && sharedPreferences?.getTanggal() != date){
                    SweetAlertDialog(this@ApsensiDetail, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("  Anda sudah ambil absen coba besok lagi  ")
                        .setConfirmText("OK")
                        .show()
                }else {
                    SweetAlertDialog(this@ApsensiDetail, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("  Anda sudah ambil absen coba besok lagi  ")
                        .setConfirmText("OK")
                        .show()
                }



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
                getCurrentLocation()


            }
        }


    }

    //


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




        retrofit.InsertAbsensi(
            nip, tanggal, jam_masuk,
            alamat, keterangan, "Bearer " + sharedPreferences?.getToken()
        )
            .enqueue(object : retrofit2.Callback<AbsensiInsertResponse> {
                override fun onFailure(call: Call<AbsensiInsertResponse>, t: Throwable) {
                    Log.e("data insert failed", "" + t.message)
                    SweetAlertDialog(this@ApsensiDetail, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("  jaringan tidak ada atau bermasalah   ")
                        .setConfirmText("OK")
                        .show()
                }

                override fun onResponse(
                    call: Call<AbsensiInsertResponse>,
                    response: Response<AbsensiInsertResponse>
                ) {
                    val dataAbsensi = response.body()
                    if (response.isSuccessful) {



                        sharedPreferences?.saveIdAbsensi(IDABSENSI, dataAbsensi!!.data.id_absensi)
                        sharedPreferences?.saveTanggal(TANGGAL, dataAbsensi!!.data.tanggal)
                        sharedPreferences?.saveJam(JAM, dataAbsensi!!.data.jam_selesai)

                        Log.e("data id masuk", " " + sharedPreferences?.getIdAbsensi())

                    }else{
                        SweetAlertDialog(this@ApsensiDetail, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("  alamat tidak terdeteksi   ")
                            .setConfirmText("OK")
                            .show()

                    }

                    Log.e("data abnsensi tersimpan", "")

                }

            })


    }



    fun GetData() {
        val retrofit = ApiServices.restApi()
        Log.e("data id", "" + sharedPreferences?.getToken())
        retrofit.getPegawai(
            sharedPreferences?.getNip()!!,
            "Bearer " + sharedPreferences?.getToken()
        )
            .enqueue(object : retrofit2.Callback<PegawaiRespon> {
                override fun onFailure(call: Call<PegawaiRespon>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<PegawaiRespon>,
                    response: Response<PegawaiRespon>
                ) {


                    val data = response.body()?.data
                    if (response.isSuccessful) {

                        if (data?.pegawai == null) {

                            SweetAlertDialog(this@ApsensiDetail, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("  isi profile dahulu   ")
                                .setConfirmText("OK")
                                .show()


                        } else {
                            tambahAbsensi()


                        }

                    } else {

                        Log.e("data tidak masuk", "$data")
                    }
                }

            })
    }





    private fun getTime() {
        val retrofit = ApiServices.restApi()

        retrofit.getTime("Bearer " + sharedPreferences?.getToken())
            .enqueue(object : retrofit2.Callback<timeResponse> {
                override fun onFailure(call: Call<timeResponse>, t: Throwable) {
                    Log.e("data_jam", "data jam " + t.message)
                }

                override fun onResponse(
                    call: Call<timeResponse>,
                    response: Response<timeResponse>
                ) {
                    val dataWaktu = response.body()
                    if (response.isSuccessful) {
                        tvTanggal.text = dataWaktu?.data?.date_time
                      hour = dataWaktu!!.data.Hour
                      //  hour = 17
                        waktu = dataWaktu.data.jam
                        minute = dataWaktu.data.minute
                        date = dataWaktu.data.date
                    //     date = "2021-09-30"

                    }

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
                    SweetAlertDialog(this@ApsensiDetail, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("  jaringan tidak ada atau bermasalah   ")
                        .setConfirmText("OK")
                        .show()

                      }

                override fun onResponse(
                    call: Call<AbsensiInsertResponse>,
                    response: Response<AbsensiInsertResponse>
                ) {
                    val dataWaktu = response.body()
                   if (response.isSuccessful){

                       sharedPreferences?.saveJam(JAM, dataWaktu!!.data.jam_selesai)

                   }


                    Log.e("data abnsensi tersimpan", "")

                }

            })


    }

    val timer = object : CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {


        }

        override fun onFinish() {
            cancel()
            alertDialog.dismiss()

        }
    }




    private fun getCurrentLocation() {

        val locationRequest = LocationRequest()
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


//                        tvLatitude.text = "$latitude"

                        Log.e("latitude", "$latitude")
//                        tvLongitude.text = "Longitude: " + longitude

                        addresses = geocoder.getFromLocation(latitude, longitude, 1)


                        val address: String = addresses[0].getAddressLine(0)
                        tvAddress.text = address

                        lokasi = address

                     //   jarak = getDistance(    -0.9094216623870202,  100.35502462911867, latitude, longitude)
                         jarak = getDistance(    -0.9094216623870202,  100.35502462911867, latitude, longitude)
                          //jarak = getDistance(   -0.462444, 100.401393, latitude, longitude)
                       //  jarak = getDistance(   -0.9094329061193681, 100.35503269141843, -0.9091398069107424, 100.3546317084189)
                      //  jarak = getDistance(   -0.9094329061193681, 100.35503269141843, -0.9099941692193596, 100.3556551789124)
                        // jarak = getDistance(   -0.9094329061193681, 100.35503269141843,   -0.9087005388791217, 100.35603431952079)
                         // jarak = getDistance(   -0.9094329061193681, 100.35503269141843,    -0.9104934983568482, 100.35293589302698)
                         //jarak = getDistance(   -0.9301211, 100.428890,    latitude, longitude)


                        jarakWaktu  = jarak!!/1
                        df.roundingMode = RoundingMode.FLOOR
                        jarakUkur = (df.format(jarakWaktu!!))


                        if (tvAddress != null) {
                            loader.visibility = View.GONE
                        }

                    }
                }
            }, Looper.getMainLooper())

    }


    val decimalFormat = DecimalFormat("#.##")

    //val twoDigitsF: Float = java.lang.Float.valueOf(decimalFormat.format(jarakWaktu))
    private fun tambahAbsensi() {
        df.roundingMode = RoundingMode.FLOOR
        Log.e("TAG", "jarak $jarakUkur ")
        if (jarakWaktu!! <= 50) {

            if (hour < 6) {
                //tidak bisa
                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)

                    .setContentText(" Belum bisa mengambil absen ")
                    .setConfirmText("OK")
                    .show()
                 Log.e("kehadiran", " bisa ambil absen karean jam 6 kurang ")


            } else if (hour >= 6 && hour <= 7) {
                if (hour == 6) {
                    alertDialog.show()
                    timer.start()


                    data = "hadir"
                    InsertAbsensi(
                        sharedPreferences?.getNip()!!,
                        "$date",
                        "$waktu",
                        lokasi!!,
                        data!!
                    )
                    Log.e("kehadiran", "t bisa ambil absen karena sebelum jam 7 ")


                } else if (hour == 7 && minute <= 30) {
                    alertDialog.show()
                    timer.start()
                    data = "hadir"
                    InsertAbsensi(
                        sharedPreferences?.getNip()!!,
                        "$date",
                        "$waktu",
                        "$lokasi",
                        data!!
                    )
                    Log.e("kehadiran", " bisa ambil absen karena sesudah  jam 7 sebelum jam 15 ")


                } else if (minute <= 45) {
                    alertDialog.show()
                    timer.start()
                    Log.e("kehadiran", "terlambat ambil absen karena sesudah  jam 7 30  ")

                    data = "telambat"
                    InsertAbsensi(
                        sharedPreferences?.getNip()!!,
                        "$date",
                        "$waktu",
                        "$lokasi",
                        data!!
                    )

                } else if (hour >= 16) {
                    SweetAlertDialog(this@ApsensiDetail, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("  tidak bisa mengambil absensi  ")
                        .setConfirmText("OK")
                        .show()
                } else {
                    alertDialog.show()
                    timer.start()
                    data = "alfa"
                    InsertAbsensi(
                        sharedPreferences?.getNip()!!,
                        "$date",
                        "$waktu",
                        "$lokasi",
                        data!!
                    )

                }
            } else if (hour <= 18) {

                data = " alfa"
                alertDialog.show()
                timer.start()
                InsertAbsensi(
                    sharedPreferences?.getNip()!!,
                    "$date",
                    "$waktu",
                    "$lokasi",
                    data!!
                )

            } else {
                SweetAlertDialog(this@ApsensiDetail, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("  tidak bisa mengambil absensi  ")
                    .setConfirmText("OK")
                    .show()


            }



            Log.e("bisa ambil apsen ", "$jarakWaktu")

            //jarak melebihi 50 meter
        } else {
            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText(     "  jarak anda $jarakUkur meter jauh dari lokasi    ")
                .setConfirmText("OK")
                .show()
             Log.e("tidak bisa ambil absen", "$jarakWaktu ")
        }


    }


    private fun tambahAbsensiSore() {

       if( sharedPreferences?.getIdAbsensi() == 0){
           Log.e("TAG", "data idavsebsi null " )
           Toast.makeText(applicationContext, "ambil absen pagi dahulu", Toast.LENGTH_SHORT).show()
       }else{
           if (jarakWaktu!! <=  50) {

               if (hour < 16) {
                   SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                       .setTitleText("Oops...")
                       .setContentText(" Belum bisa mengambil absen ")
                       .setConfirmText("OK")
                       .show()

                   data = "alfa"
               } else if (hour < 19) {

//
                   alertDialog.show()
                   timer.start()
                     data = "hadir"
                   InsertAbsensiSore(

                       "$waktu",
                       "$lokasi",
                       data!!
                   )


               }else{
                   SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                       .setTitleText("Oops...")
                       .setContentText(" Belum bisa mengambil absen karena sudah lewat  ")
                       .setConfirmText("OK")
                       .show()


               }

               Log.e("bisa ambil apsen ", "$jarak")
           } else {
               SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                   .setTitleText("Oops...")
                   .setContentText(     "  jarak anda $jarakUkur meter jauh dari lokasi    ")
                   .setConfirmText("OK")
                   .show()

               Log.e("tidak bisa ambil absen", "$jarak ")
           }

       }
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
        Log.e("latRad1", "= $latRad1 " )
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


