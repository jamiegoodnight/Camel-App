package com.jamiegood;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CopyFilesCamel {

    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:data/input?noop=true")
                        .to("log:?level=INFO&showBody=true&showHeaders=true")
                        .to("file:data/output");
            }
        });

        context.start();

        Thread.sleep(5000);

        context.stop();
    }
}


