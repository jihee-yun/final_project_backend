package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "t_guild_member")
@Getter @Setter
@NoArgsConstructor
public class GuildMember {
    @Id
    @Column(name = "guild_member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "T_User_userNum")
    public User user; // 참여자 번호

    @ManyToOne
    @JoinColumn(name = "guild_id")
    public Guild guild; // 소모임 번호
}
