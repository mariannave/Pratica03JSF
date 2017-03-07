package br.edu.ifpb.pweb.turmas.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.edu.ifpb.pweb.turmas.dao.PersistenceUtil;
import br.edu.ifpb.pweb.turmas.dao.TurmaDAO;
import br.edu.ifpb.pweb.turmas.model.Turma;

@ManagedBean(name = "turmasBean")
@ViewScoped

public class TurmasBean {
	private TurmaDAO turmaDao;
	private List<Turma> turmas;
	private Map<Long, Boolean> editavel = new HashMap<Long, Boolean>();
	private Turma turma = new Turma();
	
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
}
	
	
	public List<Turma> getTurmas() {
		return turmas;
	}
	
	public void listar(ActionEvent e) {
		turmaDao = new TurmaDAO(PersistenceUtil.getCurrentEntityManager());
		this.turmas = turmaDao.findAll();
		System.out.println(turmaDao.findAll());
		editavel = new HashMap<Long, Boolean>(this.turmas.size());
		for (Turma turma : this.turmas) {
			editavel.put(turma.getId(), false);
		}
	}
	
	public Map<Long, Boolean> getEditavel() {
		return this.editavel;
	}
	
	public void salvar(Turma turma) {
		turmaDao = new TurmaDAO(PersistenceUtil.getCurrentEntityManager());
		turmaDao.beginTransaction();
		turmaDao.update(turma);
		turmaDao.commit();
		this.editavel.put(turma.getId(), false);
	}
	
	public String excluir(Turma turma) {
		turmaDao = new TurmaDAO(PersistenceUtil.getCurrentEntityManager());
		turmaDao.beginTransaction();
		turmaDao.delete(turma);
		turmaDao.commit();
		this.turmas.remove(this.turma);
		return "listar-turmas?faces-redirect=true";
	}
	
	public String cadastrar(){
		turmaDao = new TurmaDAO(PersistenceUtil.getCurrentEntityManager());
		turmaDao.beginTransaction();
		turmaDao.insert(this.turma);
		turmaDao.commit();
		this.turma = new Turma();
		return "listar-turmas?faces-redirect=true";
	}
}
