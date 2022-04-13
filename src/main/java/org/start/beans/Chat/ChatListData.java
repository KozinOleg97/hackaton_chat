package org.start.beans.Chat;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class ChatListData {

    public List<ChatData> list;


    public String user_name;
}
