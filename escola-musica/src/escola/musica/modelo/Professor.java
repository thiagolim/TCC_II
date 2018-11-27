package escola.musica.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.caelum.stella.validation.CPFValidator;
import escola.musica.bean.ProfessorBean;
import escola.musica.util.Mensagem;

@Entity
public class Professor implements Serializable {

	private static final long serialVersionUID = -159629621796104952L;

	private Integer id;

	private String nome;

	private Date dataNascimento;

	private String cpf;

	private String email;

	private boolean ativo;

	private String telefone;
	
	private static String Login() {
		return null;
	}
	
	private static Integer Senha() {
		return null;
	}
	
	
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {

		return id;

	}

	public void setId(Integer id) {

		this.id = id;

	}

	@NotEmpty(message = "preencha o nome")

	public String getNome() {

		return nome;

	}

	public void setNome(String nome) {

		this.nome = nome;

	}

	@Temporal(TemporalType.DATE)

	@Column(name = "data_nascimento")

	@NotNull(message = "Informe a data de nascimento")

	@Past(message = "Informe uma data menor ou igual a  data atual")

	public Date getDataNascimento() {

		return dataNascimento;

	}

	public void setDataNascimento(Date dataNascimento) {

		this.dataNascimento = dataNascimento;

	}

	public String getCpf() {

		return cpf;

	}

	public void setCpf(String cpf) {

		CPFValidator validator = new CPFValidator();

		try {
			validator.assertValid(cpf);
		} catch (Exception e) {
			cpf = null;
			Mensagem.mensagemErro("CPF INVALIDO");
		}

		this.cpf = cpf;
	}

	@Email(message = "Informe um email valido")

	public String getEmail() {

		return email;

	}

	public void setEmail(String email) {

		this.email = email;

	}

	public boolean isAtivo() {

		return ativo;

	}

	public void setAtivo(boolean ativo) {

		this.ativo = ativo;

	}

	public String getTelefone() {

		return telefone;

	}

	public void setTelefone(String telefone) {

		this.telefone = telefone;

	}


	public String logar() {
		
		System.out.println(Professor.Login() + " - " + Professor.Senha());
			
		
		return "login.xhtml";
	}

	
	
	
	
	
	
	@Override

	public int hashCode() {

		final int prime = 31;

		int result = 1;

		result = prime * result + ((id == null) ? 0 : id.hashCode());

		return result;

	}

	@Override

	public boolean equals(Object obj) {

		if (this == obj)

			return true;

		if (obj == null)

			return false;

		if (getClass() != obj.getClass())

			return false;

		Professor other = (Professor) obj;

		if (id == null) {

			if (other.id != null)

				return false;

		} else if (!id.equals(other.id))

			return false;

		return true;

	}

}