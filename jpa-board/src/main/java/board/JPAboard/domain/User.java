package board.JPAboard.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor //모든 필드를 인자로 받는 생성자 자동 생성
@NoArgsConstructor  //매개변수 없는 기본 생성자 자동생성
@Builder
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId; //로그인에 사용하는 아이디
    private String password; //비밀번호
    private String nickname; //닉네임
    private LocalDateTime createdAt; //가입 시간
    private Integer receivedLikeCnt; //유저가 받은 좋아요 개수

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Board> board;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> comments;

    public void rankUp(UserRole userRole, Authentication auth){
        this.userRole = userRole;

        //현재 저장되어 있는 Authentication 수정 => 재로그인 하지 않아도 권한 수정 되기 위함
        List<GrantedAuthority> updateAuthorities = new ArrayList<>();
        updateAuthorities.add(new SimpleGrantedAuthority(userRole.name()));
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updateAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }


    public void likeChange(Integer receivedLikeCnt){
        this.receivedLikeCnt = receivedLikeCnt;
        if (this.receivedLikeCnt >= 10 && this.userRole.equals(UserRole.SILVER)){
            this.userRole = UserRole.GOLD;
        }
    }


    public void edit(String newPassword, String newNickname){
        this.password = newPassword;
        this.nickname = newNickname;
    }

    public void changRole(){
        if (userRole.equals(UserRole.BRONZE)) userRole = UserRole.SILVER;
        else if (userRole.equals(UserRole.SILVER)) userRole = UserRole.GOLD;
        else if (userRole.equals(UserRole.GOLD)) userRole = UserRole.BLACKLIST;
        else if (userRole.equals(UserRole.BLACKLIST)) userRole = UserRole.BRONZE;
    }
}
