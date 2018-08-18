package com.mine.exercise.salesnotificationmsg.broker;

import java.util.ArrayList;
import java.util.List;

import com.mine.exercise.salesnotificationmsg.dto.Message;
import com.mine.exercise.salesnotificationmsg.sub.Subscriber;

public class SaleMessageBroker implements MessageBroker {

	private List<Subscriber> subscribers = new ArrayList<>();

	private static final MessageBroker broker = new SaleMessageBroker();

	private SaleMessageBroker() {
	}

	public static MessageBroker getInstance() {
		return broker;
	}

	/* (non-Javadoc)
	 * @see com.mine.exercise.salesnotificationmsg.broker.MsgBroker#registerSubscriber(com.mine.exercise.salesnotificationmsg.sub.Subscriber)
	 */
	@Override
	public void registerSubscriber(Subscriber subscriber) {
		subscribers.add(subscriber);
	}

	/* (non-Javadoc)
	 * @see com.mine.exercise.salesnotificationmsg.broker.MsgBroker#unRegisterSubscriber(com.mine.exercise.salesnotificationmsg.sub.Subscriber)
	 */
	@Override
	public void unRegisterSubscriber(Subscriber subscriber) {
		subscriber.setActive(false);
	}

	/* (non-Javadoc)
	 * @see com.mine.exercise.salesnotificationmsg.broker.MsgBroker#removeInActiveSubscribers()
	 */
	@Override
	public void removeInActiveSubscribers() {
		// TODO:
	}

	/* (non-Javadoc)
	 * @see com.mine.exercise.salesnotificationmsg.broker.MsgBroker#processMessage(T)
	 */
	@Override
	public <T extends Message> void processMessage(T message) {
		subscribers.stream().filter(Subscriber::isActive).forEach(sub -> sub.onMessage(message));
	}

}
