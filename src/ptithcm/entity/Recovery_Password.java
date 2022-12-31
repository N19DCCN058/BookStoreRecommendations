package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Recovery_Password")
public class Recovery_Password {
	@Id
	@Column(name="Email")
	private String email;
	
	@Column(name="Code")
	private String code;

	public Recovery_Password() {
	}

	public Recovery_Password(String email, String code) {
		this.email = email;
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
