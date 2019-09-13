package tads.appweb4.ejb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import tads.appweb4.entidades.User;

@Stateless
public class UserBean {

	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;
	
	@PostConstruct
    private void initializeBean(){
    }
 
 
	public User cadastrarUsuario(String nome, String senha) {
		User user = new User();
		user.setNome(nome);
		user.setSenha(senha);
		entityManager.persist(user);
		return user;
	}

	public User login(String nome, String senha) {
		String jpql = ("select u from User u where u.nome= :pNome and u.senha= :pSenha");
        Query query = entityManager.createQuery(jpql);
        query.setParameter("pNome", nome);
        query.setParameter("pSenha", senha);
        User usuario = (User)query.getSingleResult();
		return usuario;
	}


	public User getUsuario(int id) {
		User user = entityManager.find(User.class, id);
		if (user != null)
            return user;
		return null;
	} 


	public List<User> getAllUsers() {
		String jpql = ("select u from User u");
        Query query = entityManager.createQuery(jpql, User.class);
        List<User> usuarios = query.getResultList();
        if (usuarios!=null) {
        	return usuarios;
        }
		return null;
	}


	public User getUserByToken(String token) {
		String jpql = ("select u from User u where u.token= :pToken");
        Query query = entityManager.createQuery(jpql);
        query.setParameter("pToken", token);
        User usuario = (User)query.getSingleResult();
		return usuario;
	}


	public void updateUser(User user) {	
		entityManager.merge(user);
	}
	
}
