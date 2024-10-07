package com.example.cuentasandisbank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NetworkMetricsController {

    private final NetworkMetricsBean networkMetricsBean;

    public NetworkMetricsController(NetworkMetricsBean networkMetricsBean) {
        this.networkMetricsBean = networkMetricsBean;
    }

    @GetMapping("/metrics/network")
    public Map<String, Object> getNetworkMetrics() {
        return networkMetricsBean.getNetworkUsage();
    }
}

