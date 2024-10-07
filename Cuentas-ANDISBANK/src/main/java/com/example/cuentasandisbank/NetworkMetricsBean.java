package com.example.cuentasandisbank;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NetworkMetricsBean {

    private final SystemInfo systemInfo = new SystemInfo();
    private Map<String, NetworkIF> networkInterfaces = new HashMap<>();

    @PostConstruct
    public void initializeNetworkMetrics() {
        List<NetworkIF> networkIFs = systemInfo.getHardware().getNetworkIFs();

        // Inicializar el estado inicial de las interfaces de red
        for (NetworkIF net : networkIFs) {
            net.updateAttributes(); // Actualizamos los atributos iniciales
            networkInterfaces.put(net.getName(), net);
        }
    }

    public Map<String, Object> getNetworkUsage() {
        Map<String, Object> metrics = new HashMap<>();
        List<NetworkIF> networkIFs = systemInfo.getHardware().getNetworkIFs();

        for (NetworkIF net : networkIFs) {
            net.updateAttributes(); // Actualizamos los atributos para obtener datos actuales
            long bytesSent = net.getBytesSent();
            long bytesRecv = net.getBytesRecv();

            // Añadir las métricas de esta interfaz de red al mapa de métricas
            metrics.put(net.getName() + ".bytesSent", bytesSent / (1024 * 1024) + " MB");
            metrics.put(net.getName() + ".bytesRecv", bytesRecv / (1024 * 1024) + " MB");
        }

        return metrics;
    }
}

