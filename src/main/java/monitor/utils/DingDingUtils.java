package monitor.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.IOException;

/**
 * Created by mulin on 2018/3/8.
 */
public class DingDingUtils {

    public static void SendMessage(String api, DDMessage message) {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(message));
        Request request = new Request.Builder().url(api).post(body).build();


        try {
            Response response = client.newCall(request).execute();
            String url=response.body().string();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

