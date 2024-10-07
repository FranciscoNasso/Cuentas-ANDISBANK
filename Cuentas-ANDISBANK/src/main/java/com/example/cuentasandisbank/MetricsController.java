package com.example.cuentasandisbank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MetricsController {

    private final MetricsBean metricsBean;

    public MetricsController(MetricsBean metricsBean) {
        this.metricsBean = metricsBean;
    }

    @GetMapping("/metrics/cpu-memory")
    public Map<String, Object> getCpuAndMemoryUsage() {
        Map<String, Object> metrics = new HashMap<>();

        // Obtener las métricas de CPU y memoria desde MetricsBean
        metrics.put("cpuUsage", metricsBean.getCpuUsage());
        metrics.put("heapMemoryUsed", metricsBean.getHeapMemoryUsage() + " MB");
        metrics.put("nonHeapMemoryUsed", metricsBean.getNonHeapMemoryUsage() + " MB");

        return metrics;
    }
}

