package monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by mulin on 2018/3/8.
 */
@ConfigurationProperties(prefix = "grafana")
@Component
public class GrafanaConfig {
    private String api;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private List<String> webhooks;


    public List<String> getWebhooks() {
        return webhooks;
    }

    public void setWebhooks(List<String> webhooks) {
        this.webhooks = webhooks;
    }

    private List<String> ignore;


    public List<String> getIgnore() {
        return ignore;
    }

    public void setIgnore(List<String> ignore) {
        this.ignore = ignore;
    }
}

