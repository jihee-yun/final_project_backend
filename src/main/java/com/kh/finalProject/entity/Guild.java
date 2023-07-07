package com.kh.finalProject.entity;

import com.kh.finalProject.constant.Existence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_guild")
@Getter @Setter
@NoArgsConstructor
public class Guild {
    @Id
    @Column(name = "guild_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "T_User_userNum")
    private User user;

    @Column(nullable = false)
    private String guildName;
    @Column(nullable = false)
    private int limitMember;

    @Lob
    @Column(nullable = false)
    private String detailIntro;

    @Lob
    @Column(nullable = false)
    private String intro;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private LocalDateTime meetDay;
    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private Existence existence; // 종료 여부(yes or no)

    @OneToMany(mappedBy = "guild")
    private List<GuildMember> guildMemberList = new ArrayList<>();
}
