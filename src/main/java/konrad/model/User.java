package konrad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@EqualsAndHashCode(exclude = "messages")
public class User {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    @OrderBy("creationDate DESC")
    private List<Message> messages;

    @JsonIgnore
    @ManyToMany
    private Set<User> followees = new HashSet<>();


    public User(String name) {
        this.name = name;
    }
}
