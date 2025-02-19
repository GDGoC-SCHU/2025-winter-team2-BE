package com.example.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;
    private String nickname;
    private String socialType; //
    private String socialId;   //
    private String birthDate; //
    private String gender; //

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TravelPlan> travelPlans;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<CommunityPost> communityPosts;
}
