package com.tennisPartner.tennisP.user.domain;

import com.tennisPartner.tennisP.common.domain.BaseTimeEntity;
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

    @Column
    private String userNickname;

    @Column
    private String userGender;

    @Column
    private double userNtrp;

    @Column
    private String userPhotoPath;
}
