package com.backend.IPv4.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "login_history")
public class LoginHistory {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String time;
    private String ip;
    private String location;
    
	public LoginHistory() {
		super();
	}

	public LoginHistory(Long id, String username, String time, String ip, String location) {
		super();
		this.id = id;
		this.username = username;
		this.time = time;
		this.ip = ip;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
    
    
}


