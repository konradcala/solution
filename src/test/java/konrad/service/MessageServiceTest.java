package konrad.service;

import konrad.model.Message;
import konrad.model.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
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
        Message message = messageService.createMessage(USERNAME, content);

        //then
        assertThat(message.getContent()).isEqualTo(content);
        assertThat(message.getAuthor().getName()).isEqualTo(USERNAME);
    }

    @Test
    public void shouldGetUserMessages() {
        //given
        Message first = messageService.createMessage(USERNAME, "first");
        Message second = messageService.createMessage("konrad", "second");

        //when
        User user = messageService.getUser("konrad");

        //then
        assertThat(user.getMessages()).containsExactly(second, first);
    }

    @Test
    public void shouldThrowExceptionForTooLongContent() {
        expectedException.expect(TooLongMessageException.class);
        messageService.createMessage(USERNAME, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }
}