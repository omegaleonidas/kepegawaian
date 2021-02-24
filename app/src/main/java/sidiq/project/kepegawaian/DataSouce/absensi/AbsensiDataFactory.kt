package sidiq.project.kepegawaian.DataSouce.absensi

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import sidiq.project.kepegawaian.DataSouce.KepegawaianDataSouce
import sidiq.project.kepegawaian.model.DataItem
import sidiq.project.kepegawaian.model.absensi.DataItemAbsensi

class AbsensiDataFactory : androidx.paging.DataSource.Factory<Long, DataItemAbsensi>()  {


    var mutableLivedata: MutableLiveData<AbsensiDataSource>
    var absensiDataSouce:AbsensiDataSource
    init {
        mutableLivedata = MutableLiveData()
        absensiDataSouce = AbsensiDataSource()
    }

    override fun create(): androidx.paging.DataSource<Long, DataItemAbsensi> {
        mutableLivedata.postValue(absensiDataSouce)
        return absensiDataSouce

    }

    fun getMutableLiveData(): MutableLiveData<AbsensiDataSource> {
        return mutableLivedata
    }

}