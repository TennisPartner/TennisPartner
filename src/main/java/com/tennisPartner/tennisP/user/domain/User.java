package com.tennisPartner.tennisP.user.domain;

import com.tennisPartner.tennisP.common.domain.BaseTimeEntity;
import com.tennisPartner.tennisP.user.UserGrade;
import com.tennisPartner.tennisP.user.repository.dto.UpdateUserRequestDto;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tb")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String userName;

    private String userNickname;

    private String userGender;

    private double userNtrp;

    private String userPhotoPath;

    @Column(nullable = false)
    private UserGrade userGrade;

    @Column(nullable = false)
    private String useYn;

    @PrePersist
    public void persistUser() {
        this.userGrade = UserGrade.COMMON;
        this.useYn = "Y";
    }

    public void updateUser(UpdateUserRequestDto updateUserRequestDto, String userPhotoPath) {
        this.userGender = updateUserRequestDto.getUserGender();
        this.userNickname = updateUserRequestDto.getUserNickname();
        this.userNtrp = updateUserRequestDto.getUserNtrp();
        this.userPhotoPath = userPhotoPath;
    }
    
}
