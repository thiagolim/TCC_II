package escola.musica.conversao;

import javax.faces.convert.FacesConverter;

import escola.musica.modelo.Professor;

@FacesConverter("professorConverter")

public class ProfessorConverter extends EntityConverter<Professor> {

	public ProfessorConverter() {

		super(Professor.class);

	}

}