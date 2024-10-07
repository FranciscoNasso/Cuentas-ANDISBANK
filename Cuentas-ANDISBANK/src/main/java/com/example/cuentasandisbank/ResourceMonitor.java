package com.example.cuentasandisbank;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class ResourceMonitor {

    public static void main(String[] args) {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        // Uso de CPU
        double cpuLoad = osBean.getSystemCpuLoad() * 100;
        System.out.println("Uso de CPU: " + cpuLoad + "%");

        // Uso de Memoria
        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();
        long usedMemory = totalMemory - freeMemory;
        System.out.println("Uso de Memoria: " + usedMemory / (1024 * 1024) + " MB");
    }
}
