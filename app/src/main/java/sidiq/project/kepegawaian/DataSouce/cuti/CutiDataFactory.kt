package sidiq.project.kepegawaian.DataSouce.cuti

import androidx.lifecycle.MutableLiveData

class CutiDataFactory : androidx.paging.DataSource.Factory<Long, Data>() {


    var mutabliveData: MutableLiveData<CutiDataSouce>
    var cutiDataSouce: CutiDataSouce
    init {
        mutabliveData = MutableLiveData()
        cutiDataSouce = CutiDataSouce()
    }

    override fun create(): androidx.paging.DataSource<Long, Data> {
        mutabliveData.postValue(cutiDataSouce)

        return cutiDataSouce
    }


}