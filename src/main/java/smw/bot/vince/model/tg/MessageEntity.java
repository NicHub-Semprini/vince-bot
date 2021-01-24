package smw.bot.vince.model.tg;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class MessageEntity {
	
    private final String type;
    private final Integer offset;
    private final Integer length;
    
    public MessageEntity() {
    	this("", 0, 0);
    }
}
