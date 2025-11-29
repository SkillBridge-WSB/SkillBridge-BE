package pl.wsb.merito.skillbridge.rest.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class TutorListItemResponse {
    private String id;
    private String email;
    private String name;
    private String bio;
    private String imageUrl;
    private List<SubjectListItemResponse> subjects;

    @Builder
    @Setter
    @Getter
    public static class SubjectListItemResponse {
        private String id;
        private String name;
        private Integer costPerHour;
        private String availability;
    }
}
