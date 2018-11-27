package escola.musica.login;

import escola.musica.modelo.Pessoa;

public interface IDaoPessoa {
	
	Pessoa consultarUsuario(String login, String senha);
	

		
	
}
