package sidiq.project.kepegawaian.DataSouce.absensi

import android.util.Log
import androidx.paging.PageKeyedDataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import sidiq.project.kepegawaian.Network.ApiInterface
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.model.absensi.DataItemAbsensi

class AbsensiDataSource : PageKeyedDataSource<Long,DataItemAbsensi>(){

    var api : ApiInterface

    init {
        api = ApiServices.restApi()
    }

    override fun loadAfter(
        params: LoadParams<Long>, callback:LoadCallback<Long, DataItemAbsensi>){
        api.getAbsensi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    t ->
                t.results.data .let {  callback.onResult(it,params.key +1) }
                Log.i("data masuk after ", "$t")
            },{})
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, DataItemAbsensi>
    ) {

    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, DataItemAbsensi>
    ) {
        api.getAbsensi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    t ->
                t.results.data.let { callback.onResult(it,null,2L) }
                Log.i("data masuk 1 ", "$t")
            },{})
    }
}


