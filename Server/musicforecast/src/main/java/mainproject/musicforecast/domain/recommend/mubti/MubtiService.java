package mainproject.musicforecast.domain.recommend.mubti;

import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlistTag.entity.PlaylistTag;
import mainproject.musicforecast.domain.playlistTag.repository.PlaylistTagRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MubtiService {

    private final PlaylistTagRepository playlistTagRepository;

    public MubtiService(PlaylistTagRepository playlistTagRepository) {
        this.playlistTagRepository = playlistTagRepository;
    }

    public Page<Playlist> findBySearchTags(int page, int size, int a, int b) {
        Page<PlaylistTag> playlistTags = playlistTagRepository.findByTags(PageRequest.of(page, size, Sort.by("playlist").descending()), setSearchTags(a, b));

        HashSet<Playlist> playlist = playlistTags.stream().map(playlistTag -> playlistTag.getPlaylist()).collect(Collectors.toCollection(HashSet::new));

        List<Playlist> playlistList = new ArrayList<>(playlist);

        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<>(playlistList, pageable, playlistList.size());
    }

    public List<String> setSearchTags(int a, int b) {
        List<String> genre = new ArrayList<>();

        if (a == 1) {
            switch (b) {
                case 1:
                    genre.add("우울한");
                    genre.add("차분한");
                    genre.add("센치한");
                    genre.add("잔잔한");
                    break;
                case 2:
                    genre.add("힙한");
                    genre.add("파워풀한");
                    genre.add("치명적인");
                    break;
                case 3:
                    genre.add("신나는");
                    genre.add("상쾌한");
                    break;
                case 4:
                    genre.add("몽글몽글한");
                    genre.add("편안한");
                    genre.add("행복한");
                    break;
                default:
                    genre.add("신나는");
                    break;
            }
        } else if (a == 2) {
            switch (b) {
                case 1:
                    genre.add("힙한");
                    genre.add("신나는");
                    genre.add("상쾌한");
                    genre.add("파워풀한");
                    genre.add("웅장한");
                    break;
                case 2:
                    genre.add("몽글몽글한");
                    genre.add("편안한");
                    genre.add("행복한");
                    genre.add("힙한");
                    genre.add("하이틴");
                    genre.add("신나는");
                    genre.add("가벼운");
                    break;
                case 3:
                    genre.add("차분한");
                    genre.add("편안한");
                    genre.add("잔잔한");
                    genre.add("센치한");
                    break;
                case 4:
                    genre.add("힙한");
                    genre.add("신나는");
                    genre.add("편안한");
                    genre.add("차분한");
                    break;
                default:
                    genre.add("신나는");
                    break;
            }
        } else if (a == 3) {
            switch (b) {
                case 1:
                    genre.add("신나는");
                    genre.add("힙한");
                    genre.add("상쾌한");
                    genre.add("가벼운");
                    break;
                case 2:
                    genre.add("잔잔한");
                    genre.add("차분한");
                    genre.add("따뜻한");
                    genre.add("감성적인");
                    break;
                case 3:
                    genre.add("클래식");
                    break;
                case 4:
                    // adminSuggest
                    break;
                default:
                    genre.add("신나는");
                    break;
            }
        } else {
            // redirect
        }
        return genre;
    }
}
