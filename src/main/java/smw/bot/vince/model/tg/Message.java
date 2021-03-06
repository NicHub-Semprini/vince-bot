package smw.bot.vince.model.tg;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Message {

    private final Integer message_id;
    private final User from; // optional
    private final Integer date;
    private final Chat chat;
    private final String text; // optional
    private final MessageEntity[] entities; // optional
    private final User[] new_chat_members;
    
    public Message() {
		this(0, new User(), 0, new Chat(), "", null, null);
    }
    
}
