package monitor.tasks;

import com.alibaba.fastjson.JSON;
import monitor.config.GrafanaConfig;
import monitor.config.QiNiuConfig;
import monitor.tasks.model.AlterModel;
import monitor.tasks.model.EvalMatches;
import monitor.utils.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mulin on 2018/3/8.
 */

@Component
public class GrafanaScheduled {

    @Autowired
    private GrafanaConfig config;

    @Autowired
    private QiNiuConfig qiNiuConfig;

    private static final Logger log = LoggerFactory.getLogger(GrafanaScheduled.class);


    private static HashMap<String, String> monitor_dic = new HashMap<>();


    @Scheduled(cron = "0/10 * * * * ?")
    public void test() {
        log.info("is run");
    }

    @Scheduled(cron = "* * 8 * * ?")
    public void work_notify() {
        DDMessage meesgae = new DDMessage();
        meesgae.setTitle("新的一天开始预祝【无BUG/无报警】");

    }


    @Scheduled(cron = "0/2 * 8-23 * * ?")
    public void monitor() {
        log.info("start request grafana");
        OkHttpClient httpClient = new OkHttpClient();
        String alter_api = "/api/alerts/";
        Request request = new Request.Builder()
                .url(config.getApi() + alter_api).addHeader("Authorization", config.getToken())
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            String body = response.body().string();
            List<AlterModel> alters = JSON.parseArray(body, AlterModel.class);
            alters.stream().filter(model -> !filter(model)).forEach(model -> {
                log.info("监控异常:" + JSON.toJSONString(model));
                try {
                    SendMessageToDingDing(model);
                } catch (Exception ex) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AlterModel getAlterDetails(String id) {

        OkHttpClient httpClient = new OkHttpClient();
        String alter_api = String.format("/api/alerts/%s", id);
        Request request = new Request.Builder()
                .url(config.getApi() + alter_api).addHeader("Authorization", config.getToken())
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            String body = response.body().string();
            AlterModel alter = JSON.parseObject(body, AlterModel.class);

            return alter;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private boolean filter(AlterModel model) {

        boolean filter = true;
        String current_status = model.getState();
        if (current_status.equals("alerting")) {
            filter = false;
        }
        if (monitor_dic.containsKey(model.getId())) {
            String status = monitor_dic.get(model.getId());
            if (status.equals("alerting") && current_status.equals("ok")) {
                filter = false;
            }
            monitor_dic.remove(model.getId());
        }
        monitor_dic.put(model.getId(), model.getState());
        return filter;
    }


    public String DownloadPicture(AlterModel model) {

        String configurl = config.getApi();
        String model_url = String.format("%s/%s", model.getDashboardUid(), model.getDashboardSlug());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date exceptiontime = null;
        try {
            exceptiontime = format.parse(model.getNewStateDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(exceptiontime);
        calendar.add(Calendar.MINUTE, -30);
        long from = calendar.getTime().getTime();
        long to = new Date().getTime();
        String url = String.format("%s/render/d-solo/%s?refresh=1m&orgId=1&panelId=%s&from=%d&to=%d&width=1000&height=500", configurl, model_url, model.getPanelId(), from, to);

        OkHttpClient client = new OkHttpClient();
        String token = config.getToken();
        Request request = new Request.Builder().url(url).addHeader("Authorization", token).build();

        try {
            Response response = client.newCall(request).execute();
            InputStream input = response.body().byteStream();
            String filename = QiNiuUtils.SavePic(input, qiNiuConfig);

            return "http://p5bg32tij.bkt.clouddn.com/" + filename;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void SendMessageToDingDing(AlterModel model) {
        config.getWebhooks().forEach(web_hook -> {
            DDMessage meesgae = new DDMessage();
            meesgae.setMsgtype("actionCard");
            ActionCard actionCard = new ActionCard();
            actionCard.setTitle(model.getName());
            StringBuilder builder = new StringBuilder();
            String imageUrl = DownloadPicture(model);
            builder.append("![image](" + imageUrl + ")\n");
            builder.append("## " + model.getName() + "_状态:" + model.getState() + "\n");
            builder.append("时间: **" + model.getNewStateDate() + "**\n");
            //获取真实的报错报文
            AlterModel detail = getAlterDetails(model.getId());
            if (detail.getEvalData() != null) {
                if (detail.getEvalData().getEvalMatches() != null) {
                    for (EvalMatches match : detail.getEvalData().getEvalMatches()) {
                        builder.append("- " + match.getMetric() + ":" + match.getValue() + "\n");
                    }
                }
            }
            actionCard.setText(builder.toString());
            actionCard.setBtnOrientation("0");
            actionCard.setHideAvatar("0");
            Button btn = new Button();
            btn.setTitle("详情");
            btn.setActionURL(imageUrl);
            List<Button> btns = new ArrayList<>();
            btns.add(btn);
            actionCard.setBtns(btns);
            meesgae.setActionCard(actionCard);
            DingDingUtils.SendMessage(web_hook, meesgae);

        });
    }
}

