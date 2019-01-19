package com.java.core.influxdb;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import metrics_influxdb.HttpInfluxdbProtocol;
import metrics_influxdb.InfluxdbReporter;
import metrics_influxdb.api.measurements.CategoriesMetricMeasurementTransformer;

import java.util.concurrent.TimeUnit;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2019/1/4
 */
public class InfluxdbMain {

    public static void main(String[] args) {

        MetricRegistry registry = new MetricRegistry();

        final ScheduledReporter reporter = InfluxdbReporter.forRegistry(registry)
                .protocol(new HttpInfluxdbProtocol("http", "127.0.0.1", 17586, "", "", ""))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .skipIdleMetrics(false)
                .transformer(new CategoriesMetricMeasurementTransformer("module", "artifact"))
                .build();
        reporter.start(10, TimeUnit.SECONDS);

        while (true) {}

    }

}
