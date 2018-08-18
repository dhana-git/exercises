package com.mine.exercise.salesnotificationmsg;

import com.mine.exercise.salesnotificationmsg.broker.SaleMessageBroker;
import com.mine.exercise.salesnotificationmsg.broker.MessageBroker;
import com.mine.exercise.salesnotificationmsg.dto.SaleMessage;
import com.mine.exercise.salesnotificationmsg.pub.SaleMessageProducer;
import com.mine.exercise.salesnotificationmsg.sub.SaleMessageConsumer;

public class SaleMessageProcessingDemo {

	public static void main(String[] args) {
		MessageBroker broker = SaleMessageBroker.getInstance();
		SaleMessageConsumer consumer1 = new SaleMessageConsumer("Consumer1", 1, 10);
		broker.registerSubscriber(consumer1);

		SaleMessageProducer producer1 = new SaleMessageProducer("Producer1");
		producer1.publishMessage(new SaleMessage("TV", 10)); // Message Type 1
		producer1.publishMessage(new SaleMessage("TV", 11));
		producer1.publishMessage(new SaleMessage("Mob", 5));
		producer1.publishMessage(new SaleMessage("TV", 9, 3)); // Message Type 2
		producer1.publishMessage(new SaleMessage("Lap", 20, 2));
		producer1.publishMessage(new SaleMessage("TV", 2, "add")); // Message
																	// Type 3
		producer1.publishMessage(new SaleMessage("Lap", 1, "sub"));
		producer1.publishMessage(new SaleMessage("Lap", 21)); // Message Type 1
		producer1.publishMessage(new SaleMessage("Cam", 10));
		producer1.publishMessage(new SaleMessage("Tab", 10));
		producer1.publishMessage(new SaleMessage("Router", 11));
		producer1.publishMessage(new SaleMessage("Battery", 1));
		producer1.publishMessage(new SaleMessage("LED-B32-15W", 10));
		producer1.publishMessage(new SaleMessage("LED-B32-0.5", 11));
		producer1.publishMessage(new SaleMessage("LED-B32-15W", 1));

		// Message Type 2
		producer1.publishMessage(new SaleMessage("TV", 9, 3));
		producer1.publishMessage(new SaleMessage("Lap", 20, 2));
		producer1.publishMessage(new SaleMessage("USB2-Cab", 10, 4));
		producer1.publishMessage(new SaleMessage("USB3-Cab", 11, 6));
		producer1.publishMessage(new SaleMessage("Adaptor", 9, 2));
		producer1.publishMessage(new SaleMessage("USB3-Cab", 20, 40));
		producer1.publishMessage(new SaleMessage("USB3-Cab", 5, 101));
		producer1.publishMessage(new SaleMessage("Adaptor", 21, 16));
		producer1.publishMessage(new SaleMessage("Adaptor", 10, 18));

		// Message Type 3
		producer1.publishMessage(new SaleMessage("TV", 7, "add"));
		producer1.publishMessage(new SaleMessage("USB2-Cab", 4, "mul"));

		// Message Type 1
		producer1.publishMessage(new SaleMessage("TV", 10));
		producer1.publishMessage(new SaleMessage("TV", 11));
		producer1.publishMessage(new SaleMessage("Mob", 5));
		producer1.publishMessage(new SaleMessage("Lap", 21));
		producer1.publishMessage(new SaleMessage("Cam", 10));
		producer1.publishMessage(new SaleMessage("Tab", 10));
		producer1.publishMessage(new SaleMessage("Router", 11));
		producer1.publishMessage(new SaleMessage("Battery", 1));
		producer1.publishMessage(new SaleMessage("LED-B32-15W", 10));
		producer1.publishMessage(new SaleMessage("LED-B32-0.5", 11));
		producer1.publishMessage(new SaleMessage("LED-B32-15W", 1));

	}

}
