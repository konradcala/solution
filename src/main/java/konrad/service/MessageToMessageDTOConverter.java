package konrad.service;

import konrad.model.Message;
import konrad.rest.MessageDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class MessageToMessageDTOConverter {
    MessageDTO convertToMessageJson(Message message) {
        return new MessageDTO(message.getContent(), message.getAuthor().getName(), message.getCreationDateTimestamp());
    }

    List<MessageDTO> convertToMessageJson(List<Message> messageList) {
        return messageList.stream().map(this::convertToMessageJson).collect(Collectors.toList());
    }

}
