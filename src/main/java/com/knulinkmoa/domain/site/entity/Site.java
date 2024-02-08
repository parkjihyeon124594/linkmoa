package com.knulinkmoa.domain.site.entity;

import com.knulinkmoa.domain.directory.entity.Directory;

import com.knulinkmoa.domain.member.entity.Member;
import com.knulinkmoa.domain.site.dto.request.SiteUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Site {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name= "site_name")
    private String siteName;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity=Directory.class)
    @JoinColumn(name = "directory_id")
    private Directory directory;

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    @Builder
    public Site(Long id, String url, String siteName, Directory directory) {
        this.id = id;
        this.url = url;
        this.siteName = siteName;
        this.directory = directory;
    }

    public void update(SiteUpdateRequest request){
        if (request.name() != null) {
            this.siteName = request.name();
        }

        if (request.url() != null) {
            this.url = request.url();
        }
    }

}
