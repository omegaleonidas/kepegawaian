package sidiq.project.kepegawaian.View

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.cazaea.sweetalert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import sidiq.project.kepegawaian.MainActivity
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.ID
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.KEY_TOKEN
import sidiq.project.kepegawaian.databinding.FragmentProfileBinding
import sidiq.project.kepegawaian.model.pegawai.PegawaiInsertResponse
import sidiq.project.kepegawaian.model.pegawai.PegawaiRespon
import sidiq.project.kepegawaian.model.user.userResponse
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    private var sharedPreferences: PreferenceManager? = null
    lateinit var auth: FirebaseAuth
    lateinit var navControler: NavController
    var dataAgama: Int = 0
    var dataJabatan: Int = 0
    var jenisKelamin: String? = null
    lateinit var alertDialog: SweetAlertDialog
    lateinit var alertDialog1: SweetAlertDialog
    var name: String? = null
    var namee: String? = null
    var imagee: String? = null
    var isiNip: Int = 0

    private val REQUEST_PERMISSION = 201

    var n: Long? = 0
    var alamat: String? = null
    var em: String? = null
    var pen: String? = null
    var th: String? = null
    var tgl: String? = null

    val GALLERY = 1
    val CAMERA = 2

    var uriPath: Uri? = null


    fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {

            Log.e("permisson :", "data permission setuju")
            val wallpaperDirectory = File(
                (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
            )
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                wallpaperDirectory.mkdir()
                if (wallpaperDirectory.isDirectory) {
                    Log.e("folder maASUK", " " + wallpaperDirectory.toString())

                } else {
                    Log.e("folder tidak maASUK", " " + wallpaperDirectory.toString())

                }

            } else {
                Toast.makeText(requireContext(), "Permission is required", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        sharedPreferences = PreferenceManager(requireContext())

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view

        // Inflate the layout for this fragment


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navControler = Navigation.findNavController(view)

        Log.v("data id", "" + sharedPreferences?.getToken())


        alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Selamat")
            .setContentText("data telah disimpan")
        alertDialog1 = SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Selamat")
            .setContentText("data telah di ubah")




        GetData()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val wallpaperDirectory = File(
                (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
            )
            wallpaperDirectory.mkdirs()

        } else {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
            )
            Log.e("permission", "tidak disetujui ")
        }


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        tvTglMasukInput.setOnClickListener {

            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    tvTglMasukInput.setText("" + year + "-" + month + "-" + dayOfMonth)
                },
                year,
                month,
                day
            )
            dpd.show()

        }


        tvTglLahirInput.setOnClickListener {

            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    tvTglLahirInput.setText("" + year + "-" + month + "-" + dayOfMonth)
                },
                year,
                month,
                day
            )
            dpd.show()

        }

        val agamas = resources.getStringArray(R.array.agama)
        val jabatas = resources.getStringArray(R.array.jabatan)
        val genderr = resources.getStringArray(R.array.genders)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, agamas
        )

        val adapterJabatan = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, jabatas
        )

        val adapterGenderr = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, genderr
        )


        spinnerJabatanInput.setAdapter(adapterJabatan)

        spinnerAgamaInput.setAdapter(adapter)

        SpinnerGenderInput.setAdapter(adapterGenderr)


        binding?.imageView2?.setOnClickListener {
            checkPermission()
            showPictureDialog()

        }

        spinnerAgamaInput.setOnItemClickListener(object : AdapterView.OnItemClickListener {

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                dataAgama = position + 1


            }


        })

        SpinnerGenderInput.setOnItemClickListener(object : AdapterView.OnItemClickListener {

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                jenisKelamin = parent?.getItemAtPosition(position).toString()


            }


        })


        spinnerJabatanInput.setOnItemClickListener(object : AdapterView.OnItemClickListener {

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                dataJabatan = position + 1


            }

        })



        btnLogout.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            //set title for alert dialog
            builder.setTitle(R.string.dialogTitle)
            //set message for alert dialog
            builder.setMessage(R.string.dialogMessage)
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                sharedPreferences?.saveToken(KEY_TOKEN, null)
                sharedPreferences?.saveId(ID, 0)

                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
                sharedPreferences!!.saveLoginState(PreferenceManager.LOGIN, false)
                auth.signOut()

            }
            //performing cancel action
            builder.setNeutralButton("Cancel") { dialogInterface, which ->

            }
            //performing negative action

            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()


            // navControler.navigate(R.id.action_navigation_profile_to_loginOTP2)
        }


    }

    val timer = object : CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {


        }

        override fun onFinish() {
            cancel()
            alertDialog.dismiss()

        }
    }

    val retrofit = ApiServices.restApi()

    private fun InsertData(
        nip: Int,
        nama_pegawai: String,
        jabatan_id: Int,
        email: String,
        no_tlp: String,
        alamat: String,
        tgl_masuk: String,
        tmp_lahir: String,
        id_agama: Int,
        gender: String,
        pendidikan: String,
        foto: Uri

    ) {
        retrofit.InsertPegawai(
            createPartFromString(nip.toString()),
            createPartFromString(nama_pegawai),
            createPartFromString(jabatan_id.toString()),
            createPartFromString(email),
            createPartFromString(no_tlp),
            createPartFromString(alamat),
            createPartFromString(tgl_masuk),
            createPartFromString(tmp_lahir),
            createPartFromString(id_agama.toString()),
            createPartFromString(gender),
            createPartFromString(pendidikan),
            prepareFilePart("foto", foto),
            "Bearer " + sharedPreferences?.getToken()
        ).enqueue(object : retrofit2.Callback<PegawaiInsertResponse> {
            override fun onFailure(call: Call<PegawaiInsertResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "" + t.message, Toast.LENGTH_SHORT).show()
                Log.e("data error failure", "" + t.message)
            }

            override fun onResponse(
                call: Call<PegawaiInsertResponse>,
                response: Response<PegawaiInsertResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "data berhasil response", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("data error response", " " + response.errorBody()?.string())
                } else {
                    Log.e("data error", " " + response.errorBody()?.string())
                    Toast.makeText(
                        requireContext(),
                        "data gagal response" + response.errorBody()?.string(),
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }

        })
    }


    fun UpdateUser(name: String, nip: String, nohp: String, email: String, password: String) {
        retrofit.UpdateUser(
            sharedPreferences?.getId()!!,
            name, nip, nohp, email, password, "Bearer " + sharedPreferences?.getToken()

        ).enqueue(object : retrofit2.Callback<userResponse> {
            override fun onFailure(call: Call<userResponse>, t: Throwable) {
                Log.e("update user", "onFailure: " + t.message)

            }

            override fun onResponse(call: Call<userResponse>, response: Response<userResponse>) {


                if (response.isSuccessful) {

                }
            }

        })

    }

    fun GetData() {

        Log.e("data id", "" + sharedPreferences?.getToken())
        retrofit.getPegawai(
            sharedPreferences?.getNip()!!,
            "Bearer " + sharedPreferences?.getToken()
        )
            .enqueue(object : retrofit2.Callback<PegawaiRespon> {
                override fun onFailure(call: Call<PegawaiRespon>, t: Throwable) {
                    Log.e("", t.message)
                }

                override fun onResponse(
                    call: Call<PegawaiRespon>,
                    response: Response<PegawaiRespon>
                ) {


                    val data = response.body()?.data
                    if (response.isSuccessful) {

                        n = sharedPreferences?.getNoHp()!!.toLong()
                        tvNama.text = sharedPreferences?.getNama()!!.toString()
                        Log.e("data pegawai masuk", "${data?.pegawai?.no_tlp}")

                        if (data?.pegawai != null) {

                            binding?.tvPassword?.visibility=View.VISIBLE
                            binding?.tvNip!!.setText(data.pegawai.nip)
                            binding?.tvNama!!.setText(data.pegawai.nama_pegawai)
                            binding?.tvNamaPegawaiInput!!.setText(data.pegawai.nama_pegawai)
                            // binding?.spinnerJabatanInput!!.setText(data.pegawai.nama_jabatan)
                            binding?.tvEmail!!.setText(data.pegawai.email)
                            binding?.tvTelepon!!.setText(data.pegawai.no_tlp)
                            binding?.tvAlamat!!.setText(data.pegawai.alamat_pegawai)
                            binding?.tvTglMasukInput!!.setText(data.pegawai.tgl_masuk)

                            binding?.tvTglLahirInput!!.setText(data.pegawai.tmp_lahir)
                            // binding?.spinnerAgamaInput!!.setText(data.pegawai.nama_agama)
                            //  binding?.SpinnerGenderInput!!.setText(data.pegawai.gender)
                            binding?.tvPendidikanInput!!.setText(data.pegawai.pendidikan)
                            Glide.with(binding?.imageView2!!)
                                .load("http://192.168.1.8/api/public/foto_pegawai/" + data.pegawai.foto)
                                .into(binding?.imageView2!!)

                            binding?.btnEdit!!.setText("edit")
                            btnEdit.setOnClickListener {

                                isiNip = sharedPreferences?.getNip()!!
                                namee = binding?.tvNamaPegawaiInput?.text.toString()
                                //jabatanID
                                em = binding?.tvEmail?.text.toString()

                                var no = binding?.tvTelepon!!.text.toString()
                                alamat = binding?.tvAlamat?.text.toString()
                                th = binding?.tvTglMasukInput?.text.toString()
                                tgl = binding?.tvTglLahirInput?.text.toString()
//                                id_agama
                                //gender
                                pen = binding?.tvPendidikanInput?.text.toString()
                                imagee = StringBuilder().append(data.pegawai).toString()

                                Log.e("data jenis :", "$jenisKelamin!!")

                                if (tvEmail.text.toString().length == 0) {
                                    tvEmail.setError("Email harus di isi")

                                } else if (tvTelepon.text.toString().length == 0) {
                                    tvTelepon.setError("Telepon harus di isi")
                                } else if (tv_alamat.text.toString().length == 0) {
                                    tv_alamat.setError("Alamat harus di isi")
                                } else if (tvTglMasukInput.text.toString().length == 0) {
                                    tvTglMasukInput.setError("tanggal masuk harus di isi")
                                } else if (tvTglMasukInput.text.toString().length == 0) {
                                    tvTglMasukInput.setError("tanggal masuk harus di isi")
                                } else if (SpinnerGenderInput.text.toString().length == 0) {
                                    SpinnerGenderInput.setError("jenis kelamin  harus di isi")
                                } else if (spinnerAgamaInput.text.toString().length == 0) {
                                    spinnerAgamaInput.setError("agama  harus di isi")
                                } else if (spinnerJabatanInput.text.toString().length == 0) {
                                    spinnerJabatanInput.setError("jabatan  harus di isi")
                                } else if(tvPasswordInput.text.toString().length == 0){
                                    tvPasswordInput.setError("Password  harus di isi")


                                }else {

                                    alertDialog1.show()
                                    timer.start()
                                    UpdateData(
                                        isiNip,
                                        namee!!,
                                        dataJabatan,
                                        em!!,
                                        "$no",
                                        alamat!!,
                                        th!!,
                                        tgl!!,
                                        dataAgama,
                                        jenisKelamin!!,
                                        pen!!,
                                        uriPath!!
                                        //  imagee!!.toUri()

                                    )
                                    val password = binding?.tvPasswordInput?.text.toString()
                                    UpdateUser(

                                        namee!!,
                                        isiNip.toString(),
                                        "$no",
                                        em!!,
                                        password!!

                                    )

                                }


                            }

                        } else {


                            Log.e("nip :", " " + sharedPreferences?.getNip()!!)
                            tvNip.text = sharedPreferences?.getNip().toString()
                            tvNama.text = sharedPreferences?.getNama()

                            binding?.tvTelepon!!.setText("" + sharedPreferences?.getNoHp()!!)


                            btnEdit.setOnClickListener {
                                isiNip = sharedPreferences?.getNip()!!
                                name = sharedPreferences?.getNama()
                                //jabatanID
                                em = binding?.tvEmail?.text.toString()

                                var no = binding?.tvTelepon!!.text.toString()
                                alamat = binding?.tvAlamat?.text.toString()
                                th = binding?.tvTglMasukInput?.text.toString()
                                tgl = binding?.tvTglLahirInput?.text.toString()
//                                id_agama
                                //gender
                                pen = binding?.tvPendidikanInput?.text.toString()
                                alertDialog.show()
                                timer.start()

                                InsertData(


                                    isiNip,
                                    name!!,
                                    dataJabatan,
                                    em!!,
                                    "$no",
                                    alamat!!,
                                    th!!,
                                    tgl!!,
                                    dataAgama,
                                    jenisKelamin!!,
                                    pen!!,
                                    uriPath!!
                                )

                                Log.e("email tidak ada ", "$")

                                Toast.makeText(
                                    requireContext(),
                                    " Data Pegawai Telah Disimpan ",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                            binding?.btnEdit!!.setText("simpan")

                            Toast.makeText(
                                requireContext(),
                                "data belum di tambah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {

                        Log.e("data tidak masuk", "$data")
                    }
                }

            })
    }


    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {


        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
/* if (resultCode == this.RESULT_CANCELED)
{
return
}*/
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        contentURI
                    )
                    saveImage(bitmap)
                    Toast.makeText(requireContext(), "Image Saved!", Toast.LENGTH_SHORT).show()

                    binding?.imageView2!!.setImageBitmap(bitmap)
//                   hasilFhoto = ""path

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            binding?.imageView2!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(requireContext(), "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImage(myBitmap: Bitmap) {
        val path = uri(myBitmap)
        uriPath = Uri.parse(path)
        Log.e("data fhoto", "onActivityResult: $path ")
    }

    fun uri(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
        )
// have the object build the directory structure, if needed.
        Log.d("fee", wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdir()
        } else {
            Log.e("folder", " " + wallpaperDirectory.toString())
        }
        val f = File(
            wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg")
        )
        try {
            Log.d("heel", wallpaperDirectory.toString())
            MediaScannerConnection.scanFile(
                requireContext(),
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())

            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return f.getAbsolutePath()


    }


    companion object {
        private val IMAGE_DIRECTORY = "/fhoto_pegawai"
    }


    private fun UpdateData(
        nip: Int,
        nama_pegawai: String,
        jabatan_id: Int,
        email: String,
        no_tlp: String,
        alamat: String,
        tgl_masuk: String,
        tmp_lahir: String,
        id_agama: Int,
        gender: String,
        pendidikan: String,
        foto: Uri

    ) {
        retrofit.UpdatePegawai(
            sharedPreferences?.getNip()!!,

            createPartFromString(nip.toString()),
            createPartFromString(nama_pegawai),
            createPartFromString(jabatan_id.toString()),
            createPartFromString(email),
            createPartFromString(no_tlp),
            createPartFromString(alamat),
            createPartFromString(tgl_masuk),
            createPartFromString(tmp_lahir),
            createPartFromString(id_agama.toString()),
            createPartFromString(gender),
            createPartFromString(pendidikan),
            prepareFilePart("foto", foto),
            "Bearer " + sharedPreferences?.getToken()
        ).enqueue(object : retrofit2.Callback<PegawaiInsertResponse> {
            override fun onFailure(call: Call<PegawaiInsertResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<PegawaiInsertResponse>,
                response: Response<PegawaiInsertResponse>


            ) {



            }

        })
    }

    private fun createPartFromString(descriptionString: String): RequestBody? {
        return descriptionString.toRequestBody(MultipartBody.FORM)
    }

    private fun prepareFilePart(

        name: String,
        file: Uri
    ): MultipartBody.Part? {
        val originalFile = File(file.toString())
        val filePart = originalFile
            .asRequestBody(
                context?.contentResolver?.getType(file)?.toMediaTypeOrNull()
            )
        return MultipartBody.Part.createFormData(name, originalFile.name, filePart)
    }


}
