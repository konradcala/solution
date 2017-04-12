package konrad.service;

import konrad.rest.MessageDTO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MessageServiceTest {
    private static final String USERNAME = "konrad";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Autowired
    private MessageService messageService;

    @Test
    public void shouldSaveMessage() {
        //given
        String content = "some content";

        //when
        MessageDTO message = messageService.createMessage(USERNAME, content);

        //then
        assertThat(message.getContent()).isEqualTo(content);
        assertThat(message.getAuthor()).isEqualTo(USERNAME);
    }

    @Test
    public void shouldGetUserMessages() {
        //given
        MessageDTO first = messageService.createMessage(USERNAME, "first");
        MessageDTO second = messageService.createMessage(USERNAME, "second");

        //when
        List<MessageDTO> messageDTOList = messageService.getMessagesForUser("konrad");

        //then
        assertThat(messageDTOList).containsExactly(second, first);
    }

    @Test
    public void shouldReturnEmptyMessageListForNonExistingUser() {
        //when
        expectedException.expect(UserNotFoundException.class);
        messageService.getMessagesForUser(USERNAME);
    }

    @Test
    public void shouldThrowExceptionForTooLongContent() {
        expectedException.expect(TooLongMessageException.class);
        messageService.createMessage(USERNAME, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }
}