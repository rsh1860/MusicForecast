package mainproject.musicforecast.domain.playlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public class Utils {

    public static URI createUri(String defaultUrl, long resourceId) {
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
    }

    @AllArgsConstructor
    @Getter
    public static class SingleResponseDto<T> {
        private T data;
    }

    @Getter
    public static class MultiResponseDto<T> {
        private List<T> data;
        private PageInfo pageInfo;

        public MultiResponseDto(List<T> data, Page page){
            this.data = data;
            this.pageInfo = new PageInfo(page.getNumber() + 1, page.getSize(),
                    page.getTotalElements(), page.getTotalPages());
        }
    }

    @AllArgsConstructor
    @Getter
    public static class PageInfo {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;

    }
}
