package konrad.rest;

import konrad.dao.MessageDao;
import konrad.dao.UserDao;
import konrad.model.Message;
import konrad.model.User;
import konrad.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

@RestController
@RequestMapping("/messages/{username}")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping( method = RequestMethod.GET, produces = "application/json")
    public List<Message> messages(@PathVariable String username) {
        User user = messageService.getUser(username);
        return user == null ? emptyList() : user.getMessages();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Message createMessage(@PathVariable String username, @RequestBody String content) {
        return messageService.saveMessage(username, content);
    }

}
