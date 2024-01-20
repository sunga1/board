package board.JPAboard.dto;

import board.JPAboard.domain.Board;
import board.JPAboard.domain.UploadImage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardDto {
    private Long id;
    private String userLoginId;
    private String userNickname;
    private String title;
    private String body;
    private Integer likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private MultipartFile newImage;
    private UploadImage uploadImage;

    public static BoardDto of(Board board){
        return BoardDto.builder()
                .id(board.getId())
                .userLoginId(board.getUser().getLoginId())
                .userNickname(board.getUser().getNickname())
                .title(board.getTitle())
                .body(board.getBody())
                .createdAt(board.getCreatedAt())
                .lastModifiedAt(board.getLastModifiedAt())
                .likeCnt(board.getLikes().size())
                .uploadImage(board.getUploadImage())
                .build();

    }
}
