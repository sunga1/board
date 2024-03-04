package board.JPAboard.repository;

import board.JPAboard.domain.Board;
import board.JPAboard.domain.BoardCategory;
import board.JPAboard.domain.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Page<Board> findAllByCategoryAndUserUserRoleNot(BoardCategory category, UserRole userRole, PageRequest pageRequest);
    Page<Board> findAllByCategoryAndTitleContainsAndUserUserRoleNot(BoardCategory category,String title,UserRole userRole,PageRequest pageRequest);
    Page<Board> findAllByCategoryAndUserNicknameContainsAndUserUserRoleNot(BoardCategory category,String nickname,UserRole userRole,PageRequest pageRequest);
    List<Board> findAllByUserLoginId(String loginId);
    List<Board> findAllByCategoryAndUserUserRole(BoardCategory category,UserRole userRole);
    Long countAllByUserUserRole(UserRole userRole);
    Long countAllByCategoryAndUserUserRoleNot(BoardCategory category,UserRole userRole);
}

