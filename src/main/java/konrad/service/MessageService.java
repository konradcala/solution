package konrad.service;

import konrad.rest.MessageDTO;

import java.util.List;

public interface MessageService {
    MessageDTO createMessage(String username, String content);

    List<MessageDTO> getMessagesForUser(String username);
}
