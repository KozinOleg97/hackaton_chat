package org.start.beans.Chat;

import javax.enterprise.context.RequestScoped;
import java.time.ZonedDateTime;

@RequestScoped
public class MessageDataReceived {

    public ZonedDateTime timeStamp;
    public String text;


}
