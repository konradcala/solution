package konrad.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "author")
    @OrderBy("creationDateTimestamp DESC")
    private List<Message> messages;

    @ManyToMany
    private Set<User> followees = new HashSet<>();


    public User(String name) {
        this.name = name;
    }
}
