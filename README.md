# kotlin-network

### Kotlin.ver httpURLConnection
```kotlin
    // kotlin code
    val connection = URL("http://localhost:9999").openConnection()
    // This form will auto close everything when done reading the text or on an exception.
    val data = connection.getInputStream().bufferedReader().readText()
```

### Java.ver httpURLConnection
```java
    // java code
    public HttpURLConnection getHttpConnection(String urlString) {

        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return urlConnection;
    }

    public String getResult() {

        String result = "";

        try {

            InputStream inputStream = getHttpConnection("http://localhost:9999").getInputStream();
            StringBuffer buffer = new StringBuffer();

            if( inputStream == null ) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if(buffer.length() == 0) {
                return null;
            }
            result = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
```

### Kotlin.ver Retrofit API Interface
```kotlin
    interface GithubApiService {
        @GET("service/users")
        fun search(@Query("q") query : String,
                   @Query("page") page : Int,
                   @Query("per_page") perPage : Int) : Observable<Result>

        @POST("service/users")
        fun postUsage(@Body postBody : String) : Call<String>

        companion object Factory {
            fun create() : GithubApiService {
                val retrofit = Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://api.github.com/")
                        .build()

                return retrofit.create(GithubApiService::class.java)
            }
        }
    }
```
