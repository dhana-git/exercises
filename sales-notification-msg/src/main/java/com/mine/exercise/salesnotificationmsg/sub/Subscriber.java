package com.mine.exercise.salesnotificationmsg.sub;

import com.mine.exercise.salesnotificationmsg.dto.Message;

public interface Subscriber {
	public Boolean isActive();

	public void setActive(Boolean active);

	public <T extends Message> void onMessage(T message);
}
