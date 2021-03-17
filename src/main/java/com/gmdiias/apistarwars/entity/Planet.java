package com.gmdiias.apistarwars.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "TB_PLANET")
public class Planet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "VERSION", nullable = false)
	private Long version;

	@CreationTimestamp
	@Column(name = "CREATIONDATE", updatable = false, nullable = false)
	private Date creationDate;

	@UpdateTimestamp
	@Column(name = "UPDATEDATE", nullable = false)
	private Date updateDate;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CLIMATE")
	private String climate;

	@Column(name = "TERRAIN")
	private String terrain;
	
	@Column(name = "NUMFILMAPPEARANCES")
	private Long numFilmAppearances;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
	
	public Long getNumFilmAppearances() {
		return numFilmAppearances;
	}
	
	public void setNumFilmAppearances(Long numFilmAppearances) {
		this.numFilmAppearances = numFilmAppearances;
	}

}
