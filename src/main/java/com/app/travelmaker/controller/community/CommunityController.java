package com.app.travelmaker.controller.community;


import com.app.travelmaker.common.AccountSupport;
import com.app.travelmaker.constant.CommunityType;
import com.app.travelmaker.domain.community.PostDTO;
import com.app.travelmaker.entity.mebmer.Member;
import com.app.travelmaker.entity.tag.Tag;
import com.app.travelmaker.service.community.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/community/*")
public class CommunityController extends AccountSupport {

    private final CommunityService communityService;


//  글 목록
    @GetMapping("board/list")
    public List<PostDTO> goToBoardList(CommunityType communityType, Model model){

        List postList;
        System.out.println("컴 인");
        if (communityType == null){
            postList = communityService.getPostList(CommunityType.REVIEW);
        } else if (CommunityType.IMPROVEMENT.getCode().equals(communityType)){
            postList = communityService.getPostList(CommunityType.IMPROVEMENT);
        } else {
            postList = communityService.getPostList(CommunityType.COMMUNICATION);
        }

        System.out.println("postList 1번째 : " + postList.get(0));
        Long memberId = authenticationInfo().getId();

        model.addAttribute("postList", postList);
        model.addAttribute("memberId", memberId);

        return postList;
    }

//  상세 보기
    @GetMapping("board/detail/{id}")
    public ModelAndView goToBoardDetail(@PathVariable Long id){
        System.out.println("상세 페이지 로딩");
        System.out.println("게시물 id: " + id);

        ModelAndView mv = new ModelAndView();

        PostDTO postDTO = communityService.postDetail(id);

        String memberName = postDTO.getMember().getMemberName();
        System.out.println("작성자 : " + memberName);
        System.out.println("작성일 : " + postDTO.getCreateTime());


        log.info("=={}==", postDTO.toString());
        mv.setViewName("/community/board/detail");
        mv.addObject("postDTO", postDTO);
        mv.addObject("memberName", memberName);

        return mv;
    }

//  글 쓰기
    @GetMapping("board/write")
    public String goToWrite() {
        return "/community/board/write";
    }


//  글 쓴 후 상세 보기 페이지 이동
    @PostMapping("board/write")
    public RedirectView write(PostDTO postDTO, Model model, @RequestParam("tag") String tag) {
        log.info("{}", postDTO.toString());
        log.info("=[]=", tag);


        String memberName = authenticationInfo().getMemberName();
        Long memberId = authenticationInfo().getId();

        postDTO.setMember(
                Member.builder()
                        .id(memberId)
                        .build()
        );



        System.out.println("멤버 ID : " + memberId);
        System.out.println("멤버 이름 : " + memberName);
        Long id = communityService.write(postDTO);
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("id", id);
        System.out.println("Post ID : " + id);


        return new RedirectView("/community/board/detail/" + id);
    }

    @GetMapping("board/update")
    public void goToBoardUpdate(){;}



    @GetMapping("board/delete")
    public ModelAndView postDelete(Long id){
        ModelAndView mv = new ModelAndView();

        communityService.postDelete(id);

        mv.setViewName("/community/board/list");

        return mv;
    }

}
