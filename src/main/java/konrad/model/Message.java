package konrad.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(length = 140)
    private String content;
    @Column
    private long creationDateTimestamp;

    @ManyToOne(optional = false)
    private User author;

    public Message(String content, User author, long creationDateTimestamp) {
        this.content = content;
        this.author = author;
        this.creationDateTimestamp = creationDateTimestamp;
    }
}
