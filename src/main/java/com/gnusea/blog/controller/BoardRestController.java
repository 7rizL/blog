package com.gnusea.blog.controller;

import com.gnusea.blog.dto.BoardRequestDto;
import com.gnusea.blog.model.Board;
import com.gnusea.blog.repository.BoardRepository;
import com.gnusea.blog.security.UserDetailsImpl;
import com.gnusea.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardRestController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    //게시글 전체 조회
    @GetMapping("/api/board")
    public List<Board> getBoards(){
        return boardRepository.findAllByOrderByCreatedAtDesc();
    }

    //특정 게시글 조회
    @GetMapping("/api/board/{id}")
    public Board getBoard(@PathVariable Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("boardId가 존재하지 않습니다."));
        return board;
    }

    //게시글 생성
    @PostMapping("/api/board")
    public Board createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID의 username
        String username = userDetails.getUser().getUsername();
        Board board = boardService.createBoard(requestDto, username);
        return board;

    }

    //게시글 수정
    @PutMapping("/api/board/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        boardService.update(id, requestDto);
        return id;
    }

    @DeleteMapping("/api/board/{id}")
    public Long deleteBoard(@PathVariable Long id) {
        boardRepository.deleteById(id);
        return id;
    }

}