package com.tugrulhan.freethoughts.configs;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {


    @Bean
    public OpenTelemetry openTelemetry(){
        JaegerGrpcSpanExporter jaegerGrpcSpanExporter=JaegerGrpcSpanExporter.builder()
                .setEndpoint("http://localhost:4317").build();

        SdkTracerProvider sdkTracerProvider=SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(jaegerGrpcSpanExporter).build())
                .build();

        OpenTelemetrySdk openTelemetrySdk=OpenTelemetrySdk.builder()
                .setTracerProvider(sdkTracerProvider)
                .build();

        return openTelemetrySdk;
    }

    @Bean
    public Tracer tracer(OpenTelemetry openTelemetry) {
        return openTelemetry.getTracer("com.tugrulhan.freethoughts", "1.0");
    }

}
