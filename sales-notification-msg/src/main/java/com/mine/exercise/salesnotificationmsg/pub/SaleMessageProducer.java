package com.mine.exercise.salesnotificationmsg.pub;

import com.mine.exercise.salesnotificationmsg.broker.SaleMessageBroker;
import com.mine.exercise.salesnotificationmsg.dto.Message;

public class SaleMessageProducer implements Publisher {

	private String name;

	public SaleMessageProducer(String name) {
		this.name = name;
	}

	@Override
	public <T extends Message> void publishMessage(T message) {
		SaleMessageBroker.getInstance().processMessage(message);
	}

}
