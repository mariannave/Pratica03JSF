package br.edu.ifpb.pweb.turmas.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import br.edu.ifpb.pweb.turmas.dao.PersistenceUtil;
import br.edu.ifpb.pweb.turmas.dao.TurmaDAO;
import br.edu.ifpb.pweb.turmas.model.Turma;

@ManagedBean(name = "turmasBean")
@SessionScoped


public class TurmasBean {
	private TurmaDAO turmaDao = new TurmaDAO(PersistenceUtil.getCurrentEntityManager());
	private List<Turma> turmas  = turmaDao.findAll();
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
		this.turmas = turmaDao.findAll();
		editavel = new HashMap<Long, Boolean>(this.turmas.size());
		for (Turma turma : this.turmas) {
			editavel.put(turma.getId(), false);
		}
	}
	
	public Map<Long, Boolean> getEditavel() {
		System.out.println(this.editavel);
		return this.editavel;
	}
	
	public void salvar(Turma turma) {
		turmaDao.beginTransaction();
		turmaDao.update(turma);
		turmaDao.commit();
		this.editavel.put(turma.getId(), false);
	}
	
	public void excluir(Turma turma) {
		turmaDao.beginTransaction();
		turmaDao.delete(turma);
		turmaDao.commit();
	}
	
	public String cadastrar(){
		turmaDao.beginTransaction();
		turmaDao.insert(this.turma);
		turmaDao.commit();
		return "listar?faces-redirect=true";
	}
}
