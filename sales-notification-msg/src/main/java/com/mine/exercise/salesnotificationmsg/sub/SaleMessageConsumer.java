package com.mine.exercise.salesnotificationmsg.sub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mine.exercise.salesnotificationmsg.broker.SaleMessageBroker;
import com.mine.exercise.salesnotificationmsg.dto.Message;
import com.mine.exercise.salesnotificationmsg.dto.SaleMessage;

public class SaleMessageConsumer implements Subscriber {

	private Boolean active;
	private List<SaleMessage> messages;
	private List<SaleMessage> adjustmentMessages;
	private String name;
	private Integer reportingThreshold = 10;
	private Integer augmentationThreshold = 50;

	public SaleMessageConsumer(String name) {
		this.name = name;
		this.active = true;
		this.messages = new ArrayList<>();
		this.adjustmentMessages = new ArrayList<>();
	}

	public SaleMessageConsumer(String name, Integer reportingThreshold, Integer augmentationThreshold) {
		this(name);
		this.reportingThreshold = reportingThreshold;
		this.augmentationThreshold = augmentationThreshold;
	}

	@Override
	public Boolean isActive() {
		return this.active;
	}

	@Override
	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public <T extends Message> void onMessage(T message) {
		SaleMessage saleMessage = (SaleMessage) message;
		if (saleMessage.getAdjustmentOp() != null) {
			adjustmentMessages.add(saleMessage);
			applyAdjustment(saleMessage);
		} else {
			messages.add(saleMessage);
		}

		if ((messages.size() + adjustmentMessages.size()) % reportingThreshold == 0) {
			getSaleReport();
		}

		if ((messages.size() + adjustmentMessages.size()) == augmentationThreshold) {
			SaleMessageBroker.getInstance().unRegisterSubscriber(this);
			System.out.println("Application \"" + name + "\" has reached the maximum message consumption threshold \""
					+ augmentationThreshold + "\". Hence stopped consuming messages!!!");
			getAdjustmentReport();

		}

	}

	private void getSaleReport() {
		System.out.println("Here goes the Sale Report at this moment:");
		Map<String, List<SaleMessage>> groupingBySaleType = messages.stream()
				.collect(Collectors.groupingBy(SaleMessage::getType));

		System.out.println("Product Type | Value (Pound) | Number of Sales");
		groupingBySaleType.forEach((k, v) -> {
			System.out.println(k + "|" + v.stream().collect(Collectors.summingInt(SaleMessage::getValue)) + "|"
					+ v.stream().collect(Collectors.summingInt(SaleMessage::getOccurrence)));
		});

	}

	private void getAdjustmentReport() {
		System.out.println("Here goes an adjustment report:");

		Map<String, List<SaleMessage>> groupingBySaleType = adjustmentMessages.stream()
				.collect(Collectors.groupingBy(SaleMessage::getType));

		groupingBySaleType.forEach((k, v) -> {
			System.out.println("For Product Type:" + k);
			System.out.println("Value (Pound) | Adjustment Operation");
			v.forEach(msg -> {
				System.out.println(msg.getValue() + "|" + msg.getAdjustmentOp());
			});
		});
	}

	private void applyAdjustment(SaleMessage message) {
		messages.stream().filter(msg1 -> msg1.getType().equals(message.getType())).forEach(msg2 -> {
			if ("add".equals(message.getAdjustmentOp())) {
				msg2.setValue(msg2.getValue() + message.getValue());
			} else if ("sub".equals(message.getAdjustmentOp())) {
				msg2.setValue(msg2.getValue() - message.getValue());
			} else if ("mul".equals(message.getAdjustmentOp())) {
				msg2.setValue(msg2.getValue() * message.getValue());
			} else {
				System.out.println("Invalid Adjustment Operation:" + message.getAdjustmentOp()
						+ ". Supported are add, sub, and mul.");
			}
		});
	}

}
