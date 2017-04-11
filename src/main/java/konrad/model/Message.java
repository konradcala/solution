package konrad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@EqualsAndHashCode
public class Message {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(length = 140)
    private String content;
    @ManyToOne
    private User author;

    public Message(String content, User author) {
        this.content = content;
        this.author = author;
    }
}
