package konrad.rest;

import konrad.model.Message;
import konrad.model.User;
import konrad.service.MessageService;
import konrad.service.MessageServiceImpl;
import konrad.service.TooLongMessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Collections.emptyList;

@RestController
@RequestMapping("/messages/{username}")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Message> getMessagesForUser(@PathVariable String username) {
        User user = messageService.getUser(username);
        return user == null ? emptyList() : user.getMessages();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Message createMessage(@PathVariable String username, @RequestBody String content) {
        return messageService.createMessage(username, content);
    }

    @ExceptionHandler(TooLongMessageException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public Error messageTooLong() {
        return new Error(1, "Maximum length of the message is " + MessageServiceImpl.MAX_LENGTH + " characters");
    }
}
