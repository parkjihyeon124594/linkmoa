package com.knulinkmoa.domain.site.entity;

import com.knulinkmoa.domain.directory.entity.Directory;
import com.knulinkmoa.domain.site.dto.request.SiteUpdateRequset;
import jakarta.persistence.CascadeType;
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

    @Column(name="site_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "directory_id")
    private Directory directory;

    @Builder
    public Site(Long id,String url,String name,Directory directory){
        this.id=id;
        this.url = url;
        this.name=name;
        this.directory=directory;

    }
    //public record SiteSaveRequest(String url,String name, Directory directory)
    public void update(SiteUpdateRequset requset){
        if(requset.url() !=null)
            this.url=requset.url();
        if(requset.name() !=null)
            this.name=requset.name();
        // 여기서 추가: SiteUpdateRequest에서 받아온 Directory 정보로 업데이트
        if(requset.directory() != null) {
            this.directory = requset.directory();
        }
        else if (requset.directory() == null){
            log.info("최상위 경로 - 테스트(추후 삭제) ");
        }
    }
}
