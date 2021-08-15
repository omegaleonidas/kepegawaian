package sidiq.project.kepegawaian.View

import android.Manifest.permission.CAMERA
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_cuti.*
import kotlinx.android.synthetic.main.fragment_profile.*
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
    var name: String? = null
    var namee: String? = null
    var isiNip: Int = 0

    var n: Int? = 0
    var alamat: String? = null
    var em: String? = null
    var pen: String? = null
    var th: String? = null
    var tgl: String? = null
    var ph: String? = null
    private val GALLERY = 1
    private val CAMERA = 2
    var lk: Int = 0
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
        var currentUser = auth.currentUser

//        Reference

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navControler = Navigation.findNavController(view)

        Log.v("data id", "" + sharedPreferences?.getToken())

        GetData()


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        tvTglMasuk.setOnClickListener {

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

            sharedPreferences?.saveToken(KEY_TOKEN, null)
            sharedPreferences?.saveId(ID, 0)

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

            auth.signOut()

            // navControler.navigate(R.id.action_navigation_profile_to_loginOTP2)
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
        pendidikan: String

    ) {
        retrofit.InsertPegawai(
            nip,
            nama_pegawai,
            jabatan_id,
            email,
            no_tlp,
            alamat,
            tgl_masuk,
            tmp_lahir,
            id_agama,
            gender,
            pendidikan,
            "foto",
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

                        n = sharedPreferences?.getNoHp()!!
                        Log.e("data pegawai masuk", "$data")

                        if (data?.pegawai != null) {
                            binding?.tvNip!!.setText(data.pegawai.nip)
                            binding?.tvNama!!.setText(data.pegawai.nama_pegawai)
                           // binding?.spinnerJabatanInput!!.setText(data.pegawai.nama_jabatan)
                            binding?.tvEmail!!.setText(data.pegawai.email)
                            binding?.tvTelepon!!.setText(data.pegawai.no_tlp)
                            binding?.tvAlamat!!.setText(data.pegawai.alamat)
                            binding?.tvTglMasukInput!!.setText(data.pegawai.tgl_masuk)
                            binding?.tvTglLahirInput!!.setText(data.pegawai.tmp_lahir)
                           // binding?.spinnerAgamaInput!!.setText(data.pegawai.nama_agama)
                          //  binding?.SpinnerGenderInput!!.setText(data.pegawai.gender)
                            binding?.tvPendidikanInput!!.setText(data.pegawai.pendidikan)

                            binding?.btnEdit!!.setText("edit")
                            btnEdit.setOnClickListener {

                                isiNip = sharedPreferences?.getNip()!!
                                namee = binding?.tvAlamat?.text.toString()
                                //jabatanID
                                em = binding?.tvEmail?.text.toString()

                                //   val no = binding?.tvTelepon!!.setText(n!!)
                                alamat = binding?.tvAlamat?.text.toString()
                                th = binding?.tvTglMasukInput?.text.toString()
                                tgl = binding?.tvTglLahirInput?.text.toString()
//                                id_agama
                                //gender
                                pen = binding?.tvPendidikanInput?.text.toString()

                                Log.e("data jenis :", "$jenisKelamin!!")

                                UpdateData(
                                    isiNip,
                                    namee!!,
                                    dataJabatan,
                                    em!!,
                                    "$n",
                                    alamat!!,
                                    th!!,
                                    tgl!!,
                                    dataAgama,
                                    jenisKelamin!!,
                                    pen!!
                                )


                            }

                        } else {

                            Log.e("nip :", " " + sharedPreferences?.getNip()!!)
                            tvNip.setText("" + sharedPreferences?.getNip()!!)
                            binding?.tvTelepon!!.setText("" + sharedPreferences?.getNoHp()!!)


                            btnEdit.setOnClickListener {
                                isiNip = sharedPreferences?.getNip()!!
                                name = sharedPreferences?.getNama()
                                //jabatanID
                                em = binding?.tvEmail?.text.toString()

                                //   val no = binding?.tvTelepon!!.setText(n!!)
                                alamat = binding?.tvAlamat?.text.toString()
                                th = binding?.tvTglMasukInput?.text.toString()
                                tgl = binding?.tvTglLahirInput?.text.toString()
//                                id_agama
                                //gender
                                pen = binding?.tvPendidikanInput?.text.toString()


                                InsertData(
                                    isiNip,
                                    name!!,
                                    dataJabatan,
                                    em!!,
                                    "$n",
                                    alamat!!,
                                    th!!,
                                    tgl!!,
                                    dataAgama,
                                    jenisKelamin!!,
                                    pen!!
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
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {


        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
/* if (resultCode == this.RESULT_CANCELED)
{
return
}*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Toast.makeText(requireContext(), "Image Saved!", Toast.LENGTH_SHORT).show()
                    binding?.imageView2!!.setImageBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            binding?.imageView2!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(requireContext(), "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
// have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(requireContext(),
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        private val IMAGE_DIRECTORY = "/bayunugrohoweb"
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
        pendidikan: String

    ) {
        retrofit.UpdatePegawai(
            sharedPreferences?.getNip()!!
            , nip,
            nama_pegawai,
            jabatan_id,
            email,
            no_tlp,
            alamat,
            tgl_masuk,
            tmp_lahir,
            id_agama,
            gender,
            pendidikan,
            "foto",
            "Bearer " + sharedPreferences?.getToken()
        ).enqueue(object : retrofit2.Callback<PegawaiInsertResponse> {
            override fun onFailure(call: Call<PegawaiInsertResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<PegawaiInsertResponse>,
                response: Response<PegawaiInsertResponse>


            ) {
                val dataPegawai = response.body()
                if (response.isSuccessful) {

                    Log.e("data id masuk", " " + sharedPreferences?.getIdAbsensi())

                }


            }

        })
    }

}
