package smw.bot.vince.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import smw.bot.vince.model.db.NextSessionMeeting;

@Repository
public interface NextSessionMeetingRepository extends CrudRepository<NextSessionMeeting, Long> {
	
	@Query(value = "FROM next_session_meeting")
	Optional<NextSessionMeeting> findFirst();
}
