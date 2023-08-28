package com.app.travelmaker.domain.gowith;

import com.app.travelmaker.constant.GoWithRegionType;
import com.app.travelmaker.constant.MbtiType;
import com.app.travelmaker.entity.mebmer.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class GoWithDTO {
        private Long id;
        private String goWithTitle;
        private String goWithContent;
        private Long replyCount;
        private GoWithRegionType goWithRegionType;
        private MbtiType goWithMbti;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
        private Member member;

        private List<GoWithFileDTO> files = new ArrayList<>();
        private boolean deleted = Boolean.FALSE;

        @Builder
        public GoWithDTO(Long id, String goWithTitle, String goWithContent, GoWithRegionType goWithRegionType, MbtiType goWithMbti) {
                this.id = id;
                this.goWithTitle = goWithTitle;
                this.goWithContent = goWithContent;
                this.goWithRegionType = goWithRegionType;
                this.goWithMbti = goWithMbti;
        }
}
