package com.mine.exercise.salesnotificationmsg.pub;

import com.mine.exercise.salesnotificationmsg.dto.Message;

public interface Publisher {

	public <T extends Message> void publishMessage(T message);
}
