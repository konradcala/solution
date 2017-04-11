package konrad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Entity
public class Message {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(length = 140)
    private String content;
    @Column
    private Date creationDate;

    @ManyToOne(optional = false)
    private User author;

    public Message(String content, User author, Date creationDate) {
        this.content = content;
        this.author = author;
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id != null ? id.equals(message.id) : message.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
