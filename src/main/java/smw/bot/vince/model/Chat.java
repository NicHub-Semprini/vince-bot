package smw.bot.vince.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Chat {
	
    private final Long id;
    private final String type;
    
    public Chat() {
    	this(0L, "");
    }

}
