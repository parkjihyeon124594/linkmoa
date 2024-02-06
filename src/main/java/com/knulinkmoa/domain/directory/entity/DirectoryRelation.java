package com.knulinkmoa.domain.directory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectoryRelation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "child_id")
    private Long childId;

    @Column(name = "depth")
    private Long depth;

    @Builder
    public DirectoryRelation(Long id, Long parentId, Long childId, Long depth) {
        this.id = id;
        this.parentId = parentId;
        this.childId = childId;
        this.depth = depth;
    }
}
