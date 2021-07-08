package sidiq.project.kepegawaian.DataSouce.cuti

import androidx.paging.PageKeyedDataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import sidiq.project.kepegawaian.Network.ApiInterface
import sidiq.project.kepegawaian.Network.ApiServices

class CutiDataSouce :PageKeyedDataSource <Long,Data>(){

    var api :ApiInterface

    init {
        api = ApiServices.restApi()
    }


    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Data>
    ) {
//        api.InsertCuti("11111","2020-02-30","2020-04-12","pergi liburan","2020-02-25")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(){
//                t->
//                t.results.data .let {   callback.onResult(it,null,2L)}
//            }


    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Data>) {

        api.InsertCuti("11111","2020-02-30","2020-04-12","pergi liburan","2020-02-25")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                    t->
                t.results.data .let {   callback.onResult(it,params.key +1)}
            }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Data>) {

    }


}