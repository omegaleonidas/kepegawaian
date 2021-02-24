package sidiq.project.kepegawaian.DataSouce

import androidx.paging.PageKeyedDataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import sidiq.project.kepegawaian.Network.ApiInterface
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.model.DataItem


class KepegawaianDataSouce: PageKeyedDataSource<Long,DataItem>() {

        var api : ApiInterface

    init {
        api = ApiServices.restApi()
    }

      override fun loadAfter(params: PageKeyedDataSource.LoadParams<Long>, callback: PageKeyedDataSource.LoadCallback<Long, DataItem>) {
        api.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    t ->
                t.data?.let { callback.onResult(it,params.key +1) }

            },{})

    }

  override   fun loadBefore(params: PageKeyedDataSource.LoadParams<Long>, callback: PageKeyedDataSource.LoadCallback<Long, DataItem>) {

    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, DataItem>
    ) {
        api.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    t ->
                t.data.let { callback.onResult(it,null,2L) }

            },{})


    }
}


