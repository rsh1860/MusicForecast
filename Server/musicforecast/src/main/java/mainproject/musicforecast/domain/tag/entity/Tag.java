package mainproject.musicforecast.domain.tag.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mainproject.musicforecast.domain.playlistTag.entity.PlaylistTag;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;

    @Column(nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<PlaylistTag> playlistTags;

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
