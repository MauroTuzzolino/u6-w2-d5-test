package maurotuzzolino.u6_w2_d5_test.payloads;

import jakarta.validation.constraints.NotNull;

public class BookingRequestDTO {
    @NotNull
    private Long eventId;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
