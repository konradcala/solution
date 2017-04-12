package konrad.service;

import konrad.dao.MessageDao;
import konrad.dao.UserDao;
import konrad.model.Message;
import konrad.model.User;
import konrad.rest.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    public static final int MAX_LENGTH = 140;
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageToMessageDTOConverter messageConverter;

    @Override
    public MessageDTO createMessage(String username, String content) throws TooLongMessageException {
        validate(content);
        User user = userDao.findByName(username);
        if (user == null) {
            user = saveUser(username);
        }

        Message message = new Message(content, user, new Date().getTime());
        Message saved = messageDao.save(message);
        return messageConverter.convertToMessageJson(saved);
    }

    @Override
    public List<MessageDTO> getMessagesForUser(String username) {
        User user = userDao.findByName(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }
        return messageConverter.convertToMessageJson(user.getMessages());
    }


    private User saveUser(String username) {
        User user = new User(username);
        return userDao.save(user);
    }

    private void validate(String content) {
        if (content.length() > MAX_LENGTH) {
            throw new TooLongMessageException();
        }
    }
}
