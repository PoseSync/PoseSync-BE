package capstone.posesync.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SquatRequestDTO {
    // 왼쪽 고관절 좌표
    private Coordinates leftHipJoint;

    // 오른쪽 고관절 좌표
    private Coordinates rightHipJoint;
}
