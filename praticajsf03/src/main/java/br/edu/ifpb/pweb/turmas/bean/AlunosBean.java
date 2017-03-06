package br.edu.ifpb.pweb.turmas.bean;

import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.ifpb.pweb.turmas.dao.AlunoDAO;
import br.edu.ifpb.pweb.turmas.dao.PersistenceUtil;
import br.edu.ifpb.pweb.turmas.dao.TurmaDAO;
import br.edu.ifpb.pweb.turmas.model.Aluno;
import br.edu.ifpb.pweb.turmas.model.Turma;

@ManagedBean(name = "alunosBean")
@SessionScoped
public class AlunosBean {
	private Aluno aluno = new Aluno();
	private Set<Aluno> alunos;
	private Turma turma;
	private AlunoDAO alunoDao = new AlunoDAO(PersistenceUtil.getCurrentEntityManager());
	private TurmaDAO turmaDAO = new TurmaDAO(PersistenceUtil.getEntityManager());
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Set<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(Set<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Turma getTurma() {
		return this.turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	

	public String listarAlunos(Turma turma) {
		this.alunos = turma.getAlunos();
		this.turma = turma;
		return "listar-alunos?faces-redirect=true";
	}
	
	public String cadastrar(Turma turma) {
		this.aluno = new Aluno();
		return "cadastrar-aluno?faces-redirect=true";
	}
	
	public String cadastrarAluno() {
		this.aluno.setTurma(this.turma);
		alunoDao.beginTransaction();
		alunoDao.insert(this.aluno);
		alunoDao.commit();

		turmaDAO.beginTransaction();
		this.turma.addAluno(this.aluno);
		turmaDAO.update(this.turma);
		turmaDAO.commit();
		return "listar-alunos?faces-redirect=true";
	}
}
