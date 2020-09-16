package com.jamiegood.example3;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProducerAndExample {
	
	public static void main(String[] args) throws Exception {
		
		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				
				from("direct:start")
				.process(new Processor() {

					public void process(Exchange exchange) throws Exception {
						String message = exchange.getIn().getBody(String.class);
						
						message = message + "-By Jamie Goodnight";
						
//						exchange.getOut().setBody(message);
						exchange.getMessage().setBody(message);
						
					}   
					
				})
				.to("seda:end");
				
			}
			
			
		});
		
		context.start();
		
		ProducerTemplate producerTemplate = context.createProducerTemplate();
		producerTemplate.sendBody("direct:start", "Hello, everyone");
		
		ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
		String message = consumerTemplate.receiveBody("seda:end", String.class);
		
		System.out.println(message);
		
		
		
	}

}
