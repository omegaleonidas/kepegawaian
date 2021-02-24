package sidiq.project.kepegawaian.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import sidiq.project.kepegawaian.DataSouce.cuti.CutiDataFactory
import sidiq.project.kepegawaian.DataSouce.cuti.Data

class CutiViewModel : ViewModel() {

    var cutiMVVMmodel: LiveData<PagedList<Data>>

    init {
        val cutiFactory = CutiDataFactory()

        val pagelistConfig = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .setEnablePlaceholders(false)
            .build()
        cutiMVVMmodel = LivePagedListBuilder(cutiFactory,pagelistConfig)
            .build()

    }




}