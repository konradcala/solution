package konrad.service;

import konrad.dao.MessageDao;
import konrad.dao.UserDao;
import konrad.model.Message;
import konrad.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Message saveMessage(String username, String content) {
        User user = userDao.findByName(username);
        if (user == null) {
            user = saveUser(username);
        }

        Message message = new Message(content, user);
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
}
