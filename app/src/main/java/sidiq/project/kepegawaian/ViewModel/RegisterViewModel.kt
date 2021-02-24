package sidiq.project.kepegawaian.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import sidiq.project.kepegawaian.DataSouce.KepegawaianDataFactory
import sidiq.project.kepegawaian.model.DataItem

class RegisterViewModel:  ViewModel() {


    // var exucutor : Executor
    var registerMVVMModel : LiveData<PagedList<DataItem>>

    init {



        var kepegawaianFactory = KepegawaianDataFactory()

        var pageListConfig = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .setEnablePlaceholders(false)
            .build()

        registerMVVMModel = LivePagedListBuilder(kepegawaianFactory,pageListConfig)
            //  .setFetchExecutor(exucutor)
            .build()
    }

    fun getData(): LiveData<PagedList<DataItem>> {


        return registerMVVMModel
    }
}