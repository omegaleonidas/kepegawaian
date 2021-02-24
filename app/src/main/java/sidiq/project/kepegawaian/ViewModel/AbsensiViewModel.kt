package sidiq.project.kepegawaian.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import sidiq.project.kepegawaian.DataSouce.absensi.AbsensiDataFactory

import sidiq.project.kepegawaian.model.absensi.DataItemAbsensi

class AbsensiViewModel :  ViewModel(){

    var absensiMVVMModel : LiveData<PagedList<DataItemAbsensi>>

    init {

        var absensiFactory = AbsensiDataFactory()

        var pageListConfig = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .setEnablePlaceholders(false)
            .build()

        absensiMVVMModel = LivePagedListBuilder(absensiFactory,pageListConfig)
            //  .setFetchExecutor(exucutor)
            .build()
    }


}