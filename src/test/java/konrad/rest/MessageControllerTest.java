package konrad.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static konrad.service.MessageServiceImpl.MAX_LENGTH;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAndGetSortedMessages() throws Exception {
        //given
        mockMvc.perform(post("/messages/konrad").content("first message")).andExpect(status().isCreated());
        mockMvc.perform(post("/messages/konrad").content("second message")).andExpect(status().isCreated());

        //when then
        mockMvc.perform(get("/messages/konrad")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].content", is("second message")))
                .andExpect(jsonPath("$[1].content", is("first message")));
    }

    @Test
    public void shouldReturnEmptyListForNotExistingUser() throws Exception {
        mockMvc.perform(get("/messages/konrad")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(2)));
    }

    @Test
    public void shouldReturn413StatusForTooLongMessage() throws Exception {
        //given
        String tooLongMessage = new String(new char[MAX_LENGTH + 1]).replace('\0', 'a');

        //when then
        mockMvc.perform(post("/messages/konrad").content(tooLongMessage)).andExpect(status().isPayloadTooLarge())
                .andExpect(jsonPath("$.code", is(1)));
    }


}