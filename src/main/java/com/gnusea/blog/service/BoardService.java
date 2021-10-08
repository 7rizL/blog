package com.gnusea.blog.service;

import com.gnusea.blog.dto.BoardRequestDto;
import com.gnusea.blog.model.Board;
import com.gnusea.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board createBoard(BoardRequestDto requestDto, String username){
        String contentsCheck = requestDto.getContents();
        String titleCheck = requestDto.getTitle();
        if (contentsCheck.contains("script")||contentsCheck.contains("<")||contentsCheck.contains(">")){
            Board board = new Board(requestDto,username,"xss 안돼요,,하지마세요ㅠㅠ");
            boardRepository.save(board);
            return board;
        }
        if (titleCheck.contains("script")||titleCheck.contains("<")||titleCheck.contains(">")) {
            Board board = new Board("xss 안돼요,,하지마세요ㅠㅠ", username, "xss 안돼요,,하지마세요ㅠㅠ");
            boardRepository.save(board);
            return board;
        }
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Board board = new Board(requestDto, username);
        boardRepository.save(board);
        return board;
    }

    @Transactional
    public Long update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        board.update(requestDto);
        return board.getId();
    }
}
