/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kafka.streams.interactive.query.producers;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
//import kafka.streams.interactive.query.avro.PlayEvent;
import kafka.streams.interactive.query.dao.ProductRepository;
import kafka.streams.interactive.query.entity.ProductEntity;
import kafka.streams.interactive.query.services.InventoryService;
import org.mddarr.inventory.Product;
import org.mddarr.inventory.PurchaseEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class PurchaseEventProducer {


	public static void main(String... args) throws Exception {
		int b = 0;
		Statement stmt = null;
		Connection c = null;
		String row;
		String[] columns;
		UUID uuid;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://localhost:5433/productdb",
							"postgres", "postgres");
			BufferedReader br = new BufferedReader(new FileReader("stack/db/products.csv"));
			while((row = br .readLine()) != null) {
				uuid = UUID.randomUUID();
				columns = row.split(",");
				stmt = c.createStatement();
				String sql = String.format("INSERT INTO product_entity (\"id\",\"brand\",\"name\",\"price\") "
						+ "VALUES ('%s', '%s', '%s', %d );",uuid.toString(), columns[0], columns[1], Long.parseLong(columns[2]));
				stmt.executeUpdate(sql);

				System.out.println(columns[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}

		System.out.println("Opened database successfully");

//		String row;
//		String[] columns;
//		UUID uuid;
//		List<List<String>> records = new ArrayList<>();
//		try (BufferedReader br = new BufferedReader(new FileReader("stack/db/products.csv"))) {
//			while((row = br .readLine()) != null){
//				uuid =  UUID.randomUUID();
//				columns = row.split(",");
//				productRepository.save(new ProductEntity(uuid.toString(), columns[0], columns[1], Long.parseLong(columns[2])));
//				System.out.println(columns[0]);
//			}
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
//}
//
//
//		final Map<String, String> serdeConfig = Collections.singletonMap(
//				AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
//		// Set serializers and
//		final SpecificAvroSerializer<PurchaseEvent> purchaseEventSerializer = new SpecificAvroSerializer<>();
//		purchaseEventSerializer.configure(serdeConfig, false);
//		final SpecificAvroSerializer<Product> productSerializer = new SpecificAvroSerializer<>();
//		productSerializer.configure(serdeConfig, false);
//
//		final List<Product> products = Arrays.asList(new Product(1L,
//						"Fresh Fruit For Rotting Vegetables",
//						"Stpid",
//                        (long) 15.4),
//				new Product(2L,
//                        "NikeF",
//                        "Jo X10",
//                        (long) 100.4),
//				new Product(3L,
//						"AddiddDFDFas",
//						"tar",
//						450L),
//				new Product(4L,
//						"PumaJdf",
//						"ShfeDs",
//						240L)
//		);
//
//		Map<String, Object> props = new HashMap<>();
//		props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
//		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		props.put(ProducerConfig.RETRIES_CONFIG, 0);
//		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
//		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
//		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
//		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, purchaseEventSerializer.getClass());
//
//		Map<String, Object> props1 = new HashMap<>(props);
//		props1.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
//		props1.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, productSerializer.getClass());
//
//		DefaultKafkaProducerFactory<Long, Product> pf1 = new DefaultKafkaProducerFactory<>(props1);
//		KafkaTemplate<Long, Product> template1 = new KafkaTemplate<>(pf1, true);
//		template1.setDefaultTopic(InventoryService.PRODUCT_FEED);
//
//        products.forEach(product -> {
//			System.out.println("Writing product information for '" + product.getName() + "' to input topic " +
//					InventoryService.PRODUCT_FEED);
//			template1.sendDefault(product.getProductId(), product);
//		});
//
//		DefaultKafkaProducerFactory<String, PurchaseEvent> pf = new DefaultKafkaProducerFactory<>(props);
//		KafkaTemplate<String, PurchaseEvent> template = new KafkaTemplate<>(pf, true);
//		template.setDefaultTopic(InventoryService.PURCHASE_EVENTS);
//
//		final long purchase_quantity = 3;
//		final Random random = new Random();
//
//		// send a play event every 100 milliseconds
//		while (true) {
//			final Product product = products.get(random.nextInt(products.size()));
//			System.out.println("Writing purchase event for product " + product.getName() + " to input topic " +
//					InventoryService.PURCHASE_EVENTS);
//			template.sendDefault("uk", new PurchaseEvent(1L, product.getProductId(), purchase_quantity));
//
//			Thread.sleep(100L);
//		}
////		}
//	}
//}
