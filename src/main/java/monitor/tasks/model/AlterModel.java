package monitor.tasks.model;

public class AlterModel {
    private String dashboardId;
    private String dashboardSlug;
    private String dashboardUid;
    private EvalData evalData;
    private String evalDate;
    private String executionError;
    private String id;
    private String name;
    private String newStateDate;
    private String panelId;
    private String state;
    private String url;

    public String getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(String dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getDashboardSlug() {
        return dashboardSlug;
    }

    public void setDashboardSlug(String dashboardSlug) {
        this.dashboardSlug = dashboardSlug;
    }

    public String getDashboardUid() {
        return dashboardUid;
    }

    public void setDashboardUid(String dashboardUid) {
        this.dashboardUid = dashboardUid;
    }


    public String getEvalDate() {
        return evalDate;
    }

    public void setEvalDate(String evalDate) {
        this.evalDate = evalDate;
    }

    public String getExecutionError() {
        return executionError;
    }

    public void setExecutionError(String executionError) {
        this.executionError = executionError;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewStateDate() {
        return newStateDate;
    }

    public void setNewStateDate(String newStateDate) {
        this.newStateDate = newStateDate;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public EvalData getEvalData() {
        return evalData;
    }

    public void setEvalData(EvalData evalData) {
        this.evalData = evalData;
    }
}

