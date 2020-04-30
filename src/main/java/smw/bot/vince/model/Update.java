package smw.bot.vince.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Update {
	
    private final Integer update_id;
    private final Message message;
    
    public Update() {
    	this(0, new Message());
    }
}
