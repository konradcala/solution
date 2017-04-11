package konrad.service;

import konrad.dao.MessageDao;
import konrad.dao.UserDao;
import konrad.model.Message;
import konrad.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    public static final int MAX_LENGTH = 140;
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Message createMessage(String username, String content) throws TooLongMessageException {
        validate(content);
        User user = userDao.findByName(username);
        if (user == null) {
            user = saveUser(username);
        }

        Message message = new Message(content, user, new Date());
        return messageDao.save(message);
    }

    @Override
    public User getUser(String username) {
        return userDao.findByName(username);
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
