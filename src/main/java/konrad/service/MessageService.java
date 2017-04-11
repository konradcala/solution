package konrad.service;

import konrad.model.Message;
import konrad.model.User;

public interface MessageService {
    Message createMessage(String username, String content);

    User getUser(String username);
}
