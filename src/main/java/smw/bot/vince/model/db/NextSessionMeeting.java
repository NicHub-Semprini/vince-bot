package smw.bot.vince.model.db;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "next_session_meeting")
@Table(name = "next_session_meeting")
@NoArgsConstructor
@AllArgsConstructor
public class NextSessionMeeting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "next_meeting")
	private LocalDateTime nextMeeting;

}
