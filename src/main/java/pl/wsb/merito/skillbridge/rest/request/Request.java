package pl.wsb.merito.skillbridge.rest.request;


public class Request {
    private Request() {
        // Private constructor to prevent instantiation
    }

    public record UpdateUser(String name, String bio, String imageUrl) {}
    public record CreateSubject(String name, Integer costPerHour, String availability) {}
}
