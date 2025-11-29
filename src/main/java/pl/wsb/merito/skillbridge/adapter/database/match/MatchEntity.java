package pl.wsb.merito.skillbridge.adapter.database.match;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class MatchEntity {
    UUID matchedUserId;
}
