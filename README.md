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

### companion object ?
Kotlin에서는 Java나 C#과 다르게 정적 메소드 선언을 할 수 없다. 

(@JvmStatic를 이용해서 JVM이 지원하는 Static Method의 선언은 가능, 그렇지만 대부분의 경우에는 Package Level 함수를 사용하여 문제를 해결할것을 권장)

하지만 클래스를 초기화 하지 않고 내부의 코드에 접근해야 한다거나, 가령 팩토리 메소드같은 구현이 필요할 경우 

#### Companion Objects 를 사용할 수 있다.

이것을사용하면 클래스를 인스턴스화하지 않고도 Java의 Static Method와 동일한 문법으로 멤버 함수를 호출하는것이 가능하다.
