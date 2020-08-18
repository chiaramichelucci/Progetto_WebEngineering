package data.dao;

public interface DomandaDAO {
	
	Object get(String codice);
	void save(D d);	
	void update(D d); 	
	void delete(D d);
}
