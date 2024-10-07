package com.example.cuentasandisbank;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

@Component
public class MetricsBean {

    private final OperatingSystemMXBean osBean;
    private final MemoryMXBean memoryBean;

    public MetricsBean(MeterRegistry meterRegistry) {
        this.osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        this.memoryBean = ManagementFactory.getMemoryMXBean();

        // Registrar métrica para el uso de CPU
        meterRegistry.gauge("cpu.usage", osBean, OperatingSystemMXBean::getSystemCpuLoad);

        // Registrar métricas para el uso de memoria
        meterRegistry.gauge("memory.usage.heap", memoryBean,
                bean -> memoryBean.getHeapMemoryUsage().getUsed());

        meterRegistry.gauge("memory.usage.nonheap", memoryBean,
                bean -> memoryBean.getNonHeapMemoryUsage().getUsed());
    }

    public double getCpuUsage() {
        return osBean.getSystemCpuLoad() * 100;
    }

    public long getHeapMemoryUsage() {
        return memoryBean.getHeapMemoryUsage().getUsed() / (1024 * 1024); // Convertimos a MB
    }

    public long getNonHeapMemoryUsage() {
        return memoryBean.getNonHeapMemoryUsage().getUsed() / (1024 * 1024); // Convertimos a MB
    }
}



