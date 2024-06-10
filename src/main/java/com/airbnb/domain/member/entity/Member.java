package com.airbnb.domain.member.entity;

import com.airbnb.domain.common.BaseTime;
import com.airbnb.domain.common.LoginType;
import com.airbnb.domain.common.Role;
import com.airbnb.domain.member.entity.bankAccount.BankType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @ElementCollection(fetch = FetchType.EAGER) // TODO: LAZY로 할지 고민해보기
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @Column(nullable = false)
    private String name;
    private String imgUrl;
    private String refreshToken;

    @Column(name = "password", nullable = false)
    private String encodedPassword;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BankType accountBank;

    @Column(nullable = false)
    private String accountNumber;
    private LocalDateTime deletedAt;

    @Builder
    private Member(String email, LoginType loginType, List<Role> roles, String name, String imgUrl, String refreshToken, String encodedPassword, String bankName, String accountNumber) {
        this.email = email;
        this.loginType = loginType;
        this.roles = roles;
        this.name = name;
        this.imgUrl = imgUrl;
        this.refreshToken = refreshToken;
        this.encodedPassword = encodedPassword;
        this.accountBank = BankType.of(bankName);
        this.accountNumber = accountNumber;
    }
}
