package monitor.tasks;

import com.alibaba.fastjson.JSON;
import monitor.tasks.model.AlterModel;
import monitor.tasks.model.EvalData;
import org.junit.Test;

/**
 * Created by mulin on 2018/3/8.
 */

public class GrafanaScheduledTest {
    @Test
    public void downloadPicture() throws Exception {

        AlterModel model = new AlterModel();

        model.setPanelId("5");
        model.setDashboardSlug("bi-fang-xiao-xi-zhong-xin-zi-dong-ti-jiao-ding-dan-dui-lie-jian-kong");
        model.setDashboardUid("000000003");
        model.setNewStateDate("2018-03-09T12:52:02+08:00");
        GrafanaScheduled grafanaScheduled = new GrafanaScheduled();
        grafanaScheduled.DownloadPicture(model);
    }

    @Test
    public void Test() {
        AlterModel model = new AlterModel();
        model.setDashboardId("123");
        EvalData data = new EvalData();

//        EvalMatches matches = new EvalMatches();
//        matches.setMetric("order.sync");
//        matches.setValue("123");
//
//        EvalMatches matches1 = new EvalMatches();
//        matches1.setMetric("ord111er.sync");
//        matches1.setValue("12113");

//        List<EvalMatches> macthsss = new ArrayList<>();
//        macthsss.add(matches1);
//        macthsss.add(matches);
//        data.setEvalMatches(macthsss);
//        model.setEvalData(data);

        String json = JSON.toJSONString(model);

    }

}