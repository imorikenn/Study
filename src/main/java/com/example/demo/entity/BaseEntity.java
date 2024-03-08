package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
	@Column(name = "created_at", updatable = false)
	private Date createdAt;

	@Column(name = "modified_at")
	private Date modifiedAt;

	@PrePersist
	public void onPrePersist() {
		setCreatedAt(new Date());
		setModifiedAt(new Date());
	}

	@PreUpdate
	public void onPreUpdate() {
		setModifiedAt(new Date());
	}
}
