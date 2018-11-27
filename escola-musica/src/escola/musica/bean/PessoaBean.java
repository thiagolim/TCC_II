package escola.musica.bean;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import escola.musica.dao.PessoaDao;
import escola.musica.login.IDaoPessoa;
import escola.musica.login.IDaoPessoaImpl;
import escola.musica.modelo.Pessoa;


@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private PessoaDao<Pessoa> pessoaDao = new PessoaDao<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();
	

	public String salvar() {
		pessoa = pessoaDao.merge(pessoa);
		carregarPessoas();
		return "";
	}
	
	public String novo(){
		pessoa = new Pessoa();
		return "";
	}
	
	public String remove(){
		pessoaDao.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		return "";
	}
	
	@PostConstruct
	public void carregarPessoas(){
		pessoas = pessoaDao.getListEntity(Pessoa.class);
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public PessoaDao<Pessoa> pessoaDao() {
		return pessoaDao;
	}

	public void setPessoaDao(PessoaDao<Pessoa> pessoaDao) {
		this.pessoaDao = pessoaDao;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
	public String logar() {
		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());
		
		if(pessoaUser != null) {
			
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaUser.getLogin());
			
			return "/pages/layout/template.jsf";
			
		}
		
		return "index.jsf";
	}

}
