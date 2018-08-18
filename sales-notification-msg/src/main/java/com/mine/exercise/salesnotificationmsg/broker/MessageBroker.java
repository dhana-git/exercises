package com.mine.exercise.salesnotificationmsg.broker;

import com.mine.exercise.salesnotificationmsg.dto.Message;
import com.mine.exercise.salesnotificationmsg.sub.Subscriber;

public interface MessageBroker {

	void registerSubscriber(Subscriber subscriber);

	void unRegisterSubscriber(Subscriber subscriber);

	void removeInActiveSubscribers();

	<T extends Message> void processMessage(T message);

}