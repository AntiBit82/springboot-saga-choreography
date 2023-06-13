package at.antonio.saga.common.event;

import java.util.Date;
import java.util.UUID;

public interface Event {
    UUID getEventId();
    Date getEventDate();
}
