package uos.capstone.dms.domain.board;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDTO {

    private Long commentId;
    private Long postId;
    private LocalDateTime modifiedDate;

    private boolean isModified;

    private List<CommentDTO> childComments;

    private String content;

    private int likeCounts;
    private String writerId;
    private String writerName;
}