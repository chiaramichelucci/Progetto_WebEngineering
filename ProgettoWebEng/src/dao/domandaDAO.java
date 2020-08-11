package dao;

public interface domandaDAO<D> {
	
	Object get(String codice);
	void save(D d);	
	void update(D d); 	
	void delete(D d);
}
