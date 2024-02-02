package com.knulinkmoa.domain.relation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DirectoryRelation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "directory_relation_id")
    private Long id;

    @Column(name = "parent_dir_iD")
    private Long parentDirID;

    @Column(name = "child_dir_id")
    private Long childDirId;

    @Column(name = "depth")
    private Long depth;
}
