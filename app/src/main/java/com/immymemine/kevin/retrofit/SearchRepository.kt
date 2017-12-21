package com.immymemine.kevin.retrofit

import io.reactivex.Observable

/**
 * Created by quf93 on 2017-12-19.
 */
class SearchRepository(val apiService : GithubApiService) {

    fun searchUsers(location : String, language : String) : Observable<Result> {
        return apiService.search( query = "location:$location+language:$language", page = 1, perPage = 2 )
    }

    object SearchRepositoryProvider {

        private var searchRepository : SearchRepository ? = null

        fun getSearchRepository() : SearchRepository {
            if(searchRepository == null) {
                searchRepository = SearchRepository(GithubApiService.create());
            }
            return searchRepository as SearchRepository // casting
        }
    }
}