package sidiq.project.kepegawaian.DataSouce

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import sidiq.project.kepegawaian.model.DataItem

class KepegawaianDataFactory :  androidx.paging.DataSource.Factory<Long, DataItem>()  {

    var mutableLivedata: MutableLiveData<KepegawaianDataSouce>
    var kepegawaianDataSouce: KepegawaianDataSouce
    init {
        mutableLivedata = MutableLiveData()
        kepegawaianDataSouce = KepegawaianDataSouce()
    }

    override fun create(): androidx.paging.DataSource<Long, DataItem> {
        mutableLivedata.postValue(kepegawaianDataSouce)
        return kepegawaianDataSouce

    }

    fun getMutableLiveData(): MutableLiveData<KepegawaianDataSouce> {
        return mutableLivedata
    }

}