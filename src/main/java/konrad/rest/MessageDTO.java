package konrad.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class MessageDTO {
    public final String content;
    public final String author;
    public final long creationDate;
}
