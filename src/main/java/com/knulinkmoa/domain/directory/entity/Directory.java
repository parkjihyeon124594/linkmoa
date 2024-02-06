package com.knulinkmoa.domain.directory.entity;

import com.knulinkmoa.domain.directory.dto.request.UpdateRequest;

import com.knulinkmoa.domain.site.entity.Site;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Directory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "directory_id")
    private Long id;

    @Column(name = "directory_name")
    private String directoryName;

    @OneToMany(mappedBy = "directory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Site> siteList = new ArrayList<>();

    @Column(name = "parent_id")
    private Long parentId;

/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;*/

    @Builder
    public Directory(Long id, String directoryName, List<Site> siteList, Long parentId) {
        this.id = id;
        this.directoryName = directoryName;
        this.siteList = siteList;
        this.parentId = parentId;
    }

    public void update(UpdateRequest request) {
        if (request.name() != null) {
            this.directoryName = request.name();
        }

    }


}
