/*
 * Copyright (C) 2016 Jens Reimann <jreimann@redhat.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.dentrassi.camel.iec60870;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TestApplication1 {
	public static void main(final String[] args) throws Exception {
		new TestApplication1().run();
	}

	private void run() throws Exception {
		final CamelContext context = new DefaultCamelContext();

		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				from("timer:foo") //
						.setBody(simple("${random(10)}"))//
						.convertBodyTo(Float.class) //
						.to("iec60870-client:localhost:2404/0-1-2-3-4") //
						.to("iec60870-client:localhost:2405/0-1-2-3-4") //
						.setBody(simple("Timer: ${body}")) //
						.to("stream:err") //
				;

				from("iec60870-server:localhost:2404/0-1-2-3-4") //
						.setBody(simple("${body.value}")) //
						.to("iec60870-server:localhost:2404/0-1-2-3-4") //
						.setBody(simple("Server 1: ${body}")) //
						.to("stream:err") //
				;

				from("iec60870-server:localhost:2405/0-1-2-3-4") //
						.setBody(simple("${body.value}")) //
						.to("iec60870-server:localhost:2405/0-1-2-3-4") //
						.setBody(simple("Server 2: ${body}")) //
						.to("stream:err") //
				;

				from("iec60870-client:localhost:2404/0-1-2-3-4") //
						.setBody(simple("From 1: ${body}")) //
						.to("stream:err") //
				;

				from("iec60870-client:localhost:2405/0-1-2-3-4") //
						.setBody(simple("From 2: ${body}")) //
						.to("stream:err") //
				;
			}
		});

		// start

		context.start();

		// sleep

		while (true) {
			Thread.sleep(Long.MAX_VALUE);
		}
	}
}
