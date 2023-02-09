package uos.capstone.dms.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"memberImage"})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;

    @NonNull
    @Column(updatable = false, unique = true)
    private String userId;     //사용자 ID값

    @NonNull
    private String password;     //isSocial시 Token값이 저장됨

    @NonNull
    private String username;

    @NonNull
    private String nickname;

    private int gender;   //0기타 1남성 2여성

    private LocalDate birth;

    @NonNull
    private String email;

    @NonNull
    private String phoneNo;

    @NonNull
    private boolean isSocial;

    private int zipcode;
    private String street;
    private String addressDetail;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    private MemberImage memberImage;    //프로필 사진

    @Builder
    public Member(@NonNull String userId, @NonNull String password, @NonNull String username, @NonNull String nickname, int gender, LocalDate birth, @NonNull String email, @NonNull String phoneNo, @NonNull boolean isSocial, int zipcode, String street, String addressDetail, Role role, MemberImage memberImage) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.phoneNo = phoneNo;
        this.isSocial = isSocial;
        this.zipcode = zipcode;
        this.street = street;
        this.addressDetail = addressDetail;
        this.role = role;
        this.memberImage = memberImage;
    }
}