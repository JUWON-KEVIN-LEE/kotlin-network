package com.immymemine.kevin.retrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

/**
 *  val : final (read only), 초기화 이후 변할 수 없는 값
 *  var : general variable
 *  ? : nullable
 *  !! : if (null) >>> throw error
 *  const : public static
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // retrofit + kotlin
        val searchRepository = SearchRepository.SearchRepositoryProvider.getSearchRepository()

        searchRepository.searchUsers("Lagos", "Java")
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)

        // java + kotlin
        val connection = URL("http://localhost:9999").openConnection()
        // This form will auto close everything when done reading the text or on an exception.
        val data = connection.getInputStream().bufferedReader().readText()

        // 동기
        val result = GithubApiService.create().postUsage(postBody = "post body").execute().body()
        // 비동기
        GithubApiService.create().postUsage(postBody = "post body").enqueue(
                object : Callback<String> {
                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        // result
                        val resultByEnqueue = response?.body()
                    }

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
        )

    }

    fun handleResponse(result : Result) {
        text_view.text = "total count : " + result.total_count
    }

    fun handleError(error : Throwable) {
        text_view.text = "error : " + error.message
    }
}
