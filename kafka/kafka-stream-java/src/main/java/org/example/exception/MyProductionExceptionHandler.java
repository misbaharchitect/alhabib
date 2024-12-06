package org.example.exception;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ErrorHandlerContext;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;

import java.util.Map;

public class MyProductionExceptionHandler implements ProductionExceptionHandler {
    @Override
    public ProductionExceptionHandlerResponse handle(ErrorHandlerContext context, ProducerRecord<byte[], byte[]> record, Exception exception) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return ProductionExceptionHandler.super.handle(context, record, exception);
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
