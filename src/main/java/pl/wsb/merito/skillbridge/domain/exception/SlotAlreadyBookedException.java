package pl.wsb.merito.skillbridge.domain.exception;

public class SlotAlreadyBookedException extends BusinessException {
    public SlotAlreadyBookedException() {
        super("Calendar slot is already booked.");
    }
}
