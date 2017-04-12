package konrad.rest;

import konrad.service.MessageService;
import konrad.service.MessageServiceImpl;
import konrad.service.TooLongMessageException;
import konrad.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages/{username}")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<MessageDTO> getMessagesForUser(@PathVariable String username) {
        return messageService.getMessagesForUser(username);

    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO createMessage(@PathVariable String username, @RequestBody String content) {
        return messageService.createMessage(username, content);
    }

    @ExceptionHandler(TooLongMessageException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public Error messageTooLong() {
        return new Error(1, "Maximum length of the message is " + MessageServiceImpl.MAX_LENGTH + " characters");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error userDoesNotExist(UserNotFoundException exception) {
        return new Error(2, exception.getMessage());
    }
}
