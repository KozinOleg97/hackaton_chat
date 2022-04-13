package org.start.beans.Chat;

import javax.enterprise.context.RequestScoped;
import java.time.ZonedDateTime;

@RequestScoped
public class MessageDataToSend {

    public String creator;
    public ZonedDateTime time_stamp;

    public String text;



}
